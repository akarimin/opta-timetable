var autoRefreshCount = 0;
var autoRefreshIntervalId = null;

function refreshTimeTable() {
    $.getJSON("/timeTable", function (prioritizedNewDealsQueue) {
        refreshSolvingButtons(prioritizedNewDealsQueue.solverStatus != null && prioritizedNewDealsQueue.solverStatus !== "NOT_SOLVING");
        $("#score").text(""+ (prioritizedNewDealsQueue.score == null ? "" : prioritizedNewDealsQueue.score));

        const timeTableByQueueGroup = $("#timeTableByQueueGroup");
        timeTableByQueueGroup.children().remove();
        const timeTableByBroker = $("#timeTableByBroker");
        timeTableByBroker.children().remove();
        const timeTableByBrand = $("#timeTableByBrand");
        timeTableByBrand.children().remove();
        const unassignedDeals = $("#unassignedDeals");
        unassignedDeals.children().remove();

        const theadByQueueGroup = $("<thead>").appendTo(timeTableByQueueGroup);
        const headerRowByQueueGroup = $("<tr>").appendTo(theadByQueueGroup);
        headerRowByQueueGroup.append($("<th>Priority</th>"));
        $.each(prioritizedNewDealsQueue.queueGroupList, (index, group) => {
            headerRowByQueueGroup
            .append($("<th/>")
                .append($("<span/>").text(group.name))
                .append($(`<button type="button" class="ml-2 mb-1 btn btn-light btn-sm p-1"/>`)
                        .append($(`<small class="fas fa-trash"/>`))
                    .click(() => deleteQueueGroup(group))));
        });
        const theadByBroker = $("<thead>").appendTo(timeTableByBroker);
        const headerRowByBroker = $("<tr>").appendTo(theadByBroker);
        headerRowByBroker.append($("<th>Priority</th>"));
        const brokerList = [...new Set(prioritizedNewDealsQueue.dealList.map(deal => deal.broker))];
        $.each(brokerList, (index, broker) => {
            headerRowByBroker
            .append($("<th/>")
                .append($("<span/>").text(broker)));
        });
        const theadByBrand = $("<thead>").appendTo(timeTableByBrand);
        const headerRowByBrand = $("<tr>").appendTo(theadByBrand);
        headerRowByBrand.append($("<th>Priority</th>"));
        const brandList = [...new Set(prioritizedNewDealsQueue.dealList.map(deal => deal.brand))];
        $.each(brandList, (index, brand) => {
            headerRowByBrand
            .append($("<th/>")
                .append($("<span/>").text(brand)));
        });

        const tbodyByQueueGroup = $("<tbody>").appendTo(timeTableByQueueGroup);
        const tbodyByBroker = $("<tbody>").appendTo(timeTableByBroker);
        const tbodyByBrand = $("<tbody>").appendTo(timeTableByBrand);
        $.each(prioritizedNewDealsQueue.priorityList, (index, p) => {
            const rowByQueueGroup = $("<tr>").appendTo(tbodyByQueueGroup);
            rowByQueueGroup
                .append($(`<th class="align-middle"/>`)
                    .append($("<span/>").text(`P: ${p.priority}`)
                        .append($(`<button type="button" class="ml-2 mb-1 btn btn-light btn-sm p-1"/>`)
                            .append($(`<small class="fas fa-trash"/>`))
                    .click(() => deleteSLA(p)))));

            const rowByBroker = $("<tr>").appendTo(tbodyByBroker);
            rowByBroker
                .append($(`<th class="align-middle"/>`)
                    .append($("<span/>").text(`P: ${p.priority}`)));
            $.each(prioritizedNewDealsQueue.queueGroupList, (index, queueGroup) => {
                rowByQueueGroup.append($("<td/>").prop("id", `p${p.id}queueGroup${queueGroup.id}`));
            });
            const rowByBrand = $("<tr>").appendTo(tbodyByBrand);
            rowByBrand
                .append($(`<th class="align-middle"/>`)
                    .append($("<span/>").text(`P: ${p.priority}`)));

            $.each(brokerList, (index, broker) => {
                rowByBroker.append($("<td/>").prop("id", `p${p.id}broker${convertToId(broker)}`));
            });

            $.each(brandList, (index, brand) => {
                rowByBrand.append($("<td/>").prop("id", `p${p.id}brand${convertToId(brand)}`));
            });
        });

        $.each(prioritizedNewDealsQueue.dealList, (index, deal) => {
            const color = pickColor(deal.dealId);
            const lessonElementWithoutDelete = $(`<div class="card lesson" style="background-color: ${color}"/>`)
                    .append($(`<div class="card-body p-2"/>`)
                    .append($(`<h5 class="card-title mb-1"/>`).text(`Deal: ${deal.id}`))
                    .append($(`<h7 class="card-title mb-1"/>`).text(`SLA: ${deal.expectedSLA/3600}`))
                    .append($(`<p class="card-text ml-2 mb-1"/>`)
                    .append($(`<em/>`).text(`Broker: ${deal.broker}`)))
                    .append($(`<small class="ml-2 mt-1 card-text text-muted align-bottom float-right"/>`).text(`deal-id: ${deal.dealId}`))
                    .append($(`<p class="card-text ml-2"/>`).text(`Brand: ${deal.brand}`)));
            const lessonElement = lessonElementWithoutDelete.clone();
            lessonElement.find(".card-body").prepend(
                $(`<button type="button" class="ml-2 btn btn-light btn-sm p-1 float-right"/>`)
                        .append($(`<small class="fas fa-trash"/>`))
                    .click(() => deleteDeal(deal))
            );
            if (deal.priority == null || deal.queueGroup == null) {
                unassignedDeals.append(lessonElement);
            } else {
                $(`#p${deal.priority.id}queueGroup${deal.queueGroup.id}`).append(lessonElement);
                $(`#p${deal.priority.id}broker${convertToId(deal.broker)}`).append(lessonElementWithoutDelete.clone());
                $(`#p${deal.priority.id}brand${convertToId(deal.brand)}`).append(lessonElementWithoutDelete.clone());
            }
        });
    });
}

function convertToId(str) {
    // Base64 encoding without padding to avoid XSS
    return btoa(str).replace(/=/g, "");
}

function solve() {
    $.post("/timeTable/solve", function () {
        refreshSolvingButtons(true);
        autoRefreshCount = 16;
        if (autoRefreshIntervalId == null) {
            autoRefreshIntervalId = setInterval(autoRefresh, 2000);
        }
    }).fail(function(xhr, ajaxOptions, thrownError) {
        showError("Start solving failed.", xhr);
    });
}

function refreshSolvingButtons(solving) {
    if (solving) {
        $("#solveButton").hide();
        $("#stopSolvingButton").show();
    } else {
        $("#solveButton").show();
        $("#stopSolvingButton").hide();
    }
    refreshTimeTable();
}

function autoRefresh() {
    refreshTimeTable();
    autoRefreshCount--;
    if (autoRefreshCount <= 0) {
        clearInterval(autoRefreshIntervalId);
        autoRefreshIntervalId = null;
    }
}

function stopSolving() {
    $.post("/timeTable/stopSolving", function () {
        refreshSolvingButtons(false);
        refreshTimeTable();
    }).fail(function(xhr, ajaxOptions, thrownError) {
        showError("Stop solving failed.", xhr);
    });
}

function addDeal() {
    var dealId = $("#deal_dealId").val().trim();
    $.post("/deals", JSON.stringify({
        "dealId": dealId,
        "broker": $("#deal_broker").val().trim(),
        "brand": $("#deal_brand").val().trim()
    }), function () {
        refreshTimeTable();
    }).fail(function(xhr, ajaxOptions, thrownError) {
        showError("Adding deal (" + dealId + ") failed.", xhr);
    });
    $('#lessonDialog').modal('toggle');
}

function deleteDeal(deal) {
    $.delete("/deals/" + deal.id, function () {
        refreshTimeTable();
    }).fail(function(xhr, ajaxOptions, thrownError) {
        showError("Deleting deal (" + deal.name + ") failed.", xhr);
    });
}

function addSLA() {
    $.post("/slas", JSON.stringify({
        "expectedSLA": $("#expectedSLA_expectedSLA"),
    }), function () {
        refreshTimeTable();
    }).fail(function(xhr, ajaxOptions, thrownError) {
        showError("Adding SLA failed.", xhr);
    });
    $('#timeslotDialog').modal('toggle');
}

function deleteSLA(sla) {
    $.delete("/slas/" + sla.id, function () {
        refreshTimeTable();
    }).fail(function(xhr, ajaxOptions, thrownError) {
        showError("Deleting SLA (" + sla.id + ") failed.", xhr);
    });
}

function addQueueGroup() {
    var name = $("#queueGroup_name").val().trim();
    $.post("/queue-groups", JSON.stringify({
        "name": name
    }), function () {
        refreshTimeTable();
    }).fail(function(xhr, ajaxOptions, thrownError) {
        showError("Adding Queue Group (" + name + ") failed.", xhr);
    });
    $("#roomDialog").modal('toggle');
}

function deleteQueueGroup(queueGroup) {
    $.delete("/queue-groups/" + queueGroup.id, function () {
        refreshTimeTable();
    }).fail(function(xhr, ajaxOptions, thrownError) {
        showError("Deleting Queue Group (" + queueGroup.name + ") failed.", xhr);
    });
}

function showError(title, xhr) {
    const serverErrorMessage = !xhr.responseJSON ? `${xhr.status}: ${xhr.statusText}` : xhr.responseJSON.message;
    console.error(title + "\n" + serverErrorMessage);
    const notification = $(`<div class="toast" role="alert" role="alert" aria-live="assertive" aria-atomic="true" style="min-width: 30rem"/>`)
            .append($(`<div class="toast-header bg-danger">
                            <strong class="mr-auto text-dark">Error</strong>
                            <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`))
            .append($(`<div class="toast-body"/>`)
                    .append($(`<p/>`).text(title))
                    .append($(`<pre/>`)
                            .append($(`<code/>`).text(serverErrorMessage))
                    )
            );
    $("#notificationPanel").append(notification);
    notification.toast({delay: 30000});
    notification.toast('show');
}

$(document).ready( function() {
    $.ajaxSetup({
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    // Extend jQuery to support $.put() and $.delete()
    jQuery.each( [ "put", "delete" ], function( i, method ) {
        jQuery[method] = function (url, data, callback, type) {
            if (jQuery.isFunction(data)) {
                type = type || callback;
                callback = data;
                data = undefined;
            }
            return jQuery.ajax({
                url: url,
                type: method,
                dataType: type,
                data: data,
                success: callback
            });
        };
    });


    $("#refreshButton").click(function() {
        refreshTimeTable();
    });
    $("#solveButton").click(function() {
        solve();
    });
    $("#stopSolvingButton").click(function() {
        stopSolving();
    });
    $("#addLessonSubmitButton").click(function() {
        addDeal();
    });
    $("#addTimeslotSubmitButton").click(function() {
        addSLA();
    });
    $("#addRoomSubmitButton").click(function() {
        addQueueGroup();
    });

    refreshTimeTable();
});

// ****************************************************************************
// TangoColorFactory
// ****************************************************************************

const SEQUENCE_1 = [0x8AE234, 0xFCE94F, 0x729FCF, 0xE9B96E, 0xAD7FA8];
const SEQUENCE_2 = [0x73D216, 0xEDD400, 0x3465A4, 0xC17D11, 0x75507B];

var colorMap = new Map;
var nextColorCount = 0;

function pickColor(object) {
    let color = colorMap[object];
    if (color !== undefined) {
        return color;
    }
    color = nextColor();
    colorMap[object] = color;
    return color;
}

function nextColor() {
    let color;
    let colorIndex = nextColorCount % SEQUENCE_1.length;
    let shadeIndex = Math.floor(nextColorCount / SEQUENCE_1.length);
    if (shadeIndex === 0) {
        color = SEQUENCE_1[colorIndex];
    } else if (shadeIndex === 1) {
        color = SEQUENCE_2[colorIndex];
    } else {
        shadeIndex -= 3;
        let floorColor = SEQUENCE_2[colorIndex];
        let ceilColor = SEQUENCE_1[colorIndex];
        let base = Math.floor((shadeIndex / 2) + 1);
        let divisor = 2;
        while (base >= divisor) {
            divisor *= 2;
        }
        base = (base * 2) - divisor + 1;
        let shadePercentage = base / divisor;
        color = buildPercentageColor(floorColor, ceilColor, shadePercentage);
    }
    nextColorCount++;
    return "#" + color.toString(16);
}

function buildPercentageColor(floorColor, ceilColor, shadePercentage) {
    let red = (floorColor & 0xFF0000) + Math.floor(shadePercentage * ((ceilColor & 0xFF0000) - (floorColor & 0xFF0000))) & 0xFF0000;
    let green = (floorColor & 0x00FF00) + Math.floor(shadePercentage * ((ceilColor & 0x00FF00) - (floorColor & 0x00FF00))) & 0x00FF00;
    let blue = (floorColor & 0x0000FF) + Math.floor(shadePercentage * ((ceilColor & 0x0000FF) - (floorColor & 0x0000FF))) & 0x0000FF;
    return red | green | blue;
}

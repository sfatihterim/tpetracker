$(document).ready(function () {
    $.fn.dataTable.enum(["Quarterback", "Running Back", "Wide Receiver", "Tight End", "Offensive Line", "Defensive End", "Defensive Tackle", "Linebacker", "Cornerback", "Safety", "Kicker/Punter", "Unknown"]);
    var t = $("#table").DataTable({
        dom: "<'row'<'col-sm-6'B><'col-sm-6'f>><'row'<'col-sm-12'tr>><'row'<'col-sm-5'i><'col-sm-7'p>>",
        scrollX: !0,
        paging: !1,
        order: [[0, "asc"]],
        buttons: [{
            text: "Toggle All", action: function () {
                t.columns().visible(!t.column(0).visible())
            }
        }, {extend: 'colvis', text: 'Toggle Columns'}],
        data: dataSet,
        columns: [{title: "Position"}, {title: "NSFL"}, {title: "DSFL"}, {title: "Free Agents"}, {title: "Prospects"}, {title: "Total"}, {title: "NSFL Min TPE"}, {title: "NSFL Max TPE"}, {title: "NSFL Average TPE"}, {title: "DSFL Min TPE"}, {title: "DSFL Max TPE"}, {title: "DSFL Average TPE"}]
    })
});

$(document).ready(function () {
    var t = $("#table").DataTable({
        dom: "<'row'<'col-sm-6'B><'col-sm-6'f>><'row'<'col-sm-12'tr>><'row'<'col-sm-5'i><'col-sm-7'p>>",
        scrollX: !0,
        paging: !1,
        order: [[0, "desc"]],
        buttons: [{
            text: "Toggle All", action: function () {
                t.columns().visible(!t.column(0).visible())
            }
        }, {extend: 'colvis', text: 'Toggle Columns'}],
        data: dataSet,
        columns: [{title: "League"}, {title: "Name"}, {title: "Total TPE"}, {title: "Average TPE"}, {title: "Total Effective TPE"}, {title: "Average Effective TPE"}, {title: "Offensive TPE"}, {title: "Defensive TPE"}]
    })
});

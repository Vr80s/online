$(document).ready(function($){

    $('#pass').click(function () {

        var applyId = $('#applyId').val();

        $.ajax({
            type:"POST",
            url: "/medical/doctor/apply/updateStatus",
            data: {
                id: applyId,
                status: 1
            },
            async:true,
            success: function( result ) {
                console.log(result.success);
                if(result.success == true){
                    window.location.href = "/home#medical/doctor/apply/index"
                }
                else {
                    alert('修改失败');
                }
            },
            complete: function( result ) {}
        });
    });

    $('#reject').click(function () {

        var applyId = $('#applyId').val();

        $.ajax({
            type:"POST",
            url: "/medical/doctor/apply/updateStatus",
            data: {
                id: applyId,
                status: 0
            },
            async:true,
            success: function( result ) {
                console.log(result.success);
                if(result.success == true){
                    window.location.href = "/home#medical/doctor/apply/index"
                }
                else {
                    alert('修改失败,请稍后再试');
                }
            },
            complete: function( result ) {}
        });
    });

});
$(document).ready(function($){

    $('#pass').click(function () {

        // if(confirm("确定认证成功？")){
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
                    if(result.success == true){
                        window.location.href = "/home#medical/doctor/apply/index"
                    }
                    else {
                        alertInfo(result.errorMessage);
                    }
                },
                complete: function( result ) {}
            });
        // }
    });

    $('#reject').click(function () {

        // if(confirm("拒绝该认证？")){
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
                    if(result.success == true){
                        window.location.href = "/home#medical/doctor/apply/index"
                    }
                    else {
                        alertInfo(result.errorMessage);
                    }
                },
                complete: function( result ) {}
            });
        // }
    });

});
$(function() {

    $('.J-course-detail').on('click', function() {
        window.location.href = "/courses/" + $(this).data('courseid') + "/info";
    });
});
<style>
    #xczh-tip {
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translateX(-50%) translateY(-50%);
        padding: 20px;
        background-color: #000;
        color: #fff;
        opacity: .5;
        border-radius: 4px;
        z-index: 9999;
        font-size: 14px;
    }
</style>
<div id="xczh-tip" style="display: none;">

</div>
<script>
    var $tip = $('#xczh-tip');

    function tip(text) {
        $tip.text(text);
        $tip.toggle();
        setTimeout(function () {
            $tip.toggle();
            $tip.text('');
        }, 2000);
    }
</script>
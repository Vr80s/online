var labelTable;
var labelForm;
var editLabelForm;
$(function () {
        labelForm = $('#addLabelDialog-form');
        editLabelForm = $('#editLabelDialog-form');

        function createImageUpload(obj) {
            obj.ace_file_input({
                style: 'well',
                btn_choose: '点击选择图片',
                btn_change: null,
                no_icon: 'ace-icon fa fa-cloud-upload',
                droppable: true,
                thumbnail: 'small',//large | fit
                preview_error: function (filename, error_code) {
                }
            }).on('change', function () {
            });
            obj.ace_file_input('update_settings',
                {
                    'allowExt': ["jpeg", "jpg", "png", "gif", "bmp"],
                    'allowMime': ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"]
                });
            $(".remove").hide();
        }

        function reviewImage(inputId, imgSrc) {
            var $inputId = $("#" + inputId);
            var $inputIdParent = $inputId.parent();
            if (!imgSrc) return;
            var fileName = imgSrc;
            if (imgSrc.indexOf("/") > -1) {
                fileName = imgSrc.substring(imgSrc.lastIndexOf("/") + 1);
            }
            $inputIdParent.find('.ace-file-name').remove();
            $inputIdParent.find(".ace-file-container").addClass('hide-placeholder').attr('data-title', null)
                .addClass('selected').html('<span class="ace-file-name" data-title="' + fileName + '">'
                + ('<img class="middle" style="width: 50px; height: 50px;" src="' + imgSrc + '"><i class="ace-icon fa fa-picture-o file-image"></i>')
                + '</span>');
        }

        function uploadImg($this, imgId, $valId) {
            var v = $this.value.split(".")[$this.value.split(".").length - 1].toUpperCase();
            if (v !== 'BMP' && v !== 'GIF' && v !== 'JPEG' && v !== 'PNG' && v !== 'SVG' && v !== 'JPG') {
                layer.alert("图片格式错误,请重新选择.");
                this.value = "";

                return;
            }
            var id = $($this).attr("id");
            var idParent = $("#" + id).parent();
            ajaxFileUpload($this.id, basePath + "/attachmentCenter/upload?projectName=online&fileType=1", function (data) {
                if (data.error === 0) {
                    idParent.find(".ace-file-name").after("<img src='' class='middle'/>");
                    idParent.find(".ace-file-name img").attr("src", data.url);
                    idParent.parent().find(".ace-file-name img").attr("style", "width: 50px; height: 50px;");

                    $valId.val(data.url);
                    document.getElementById(imgId).focus();
                    document.getElementById(imgId).blur();
                    $(".remove").hide();
                } else {
                    alert(data.message);
                }
            })
        }

        var checkbox = '<input type="checkbox" class="ace" onclick="chooseAll(this)" /> <span class="lbl"></span>';
        var objData = [{
            "title": checkbox,
            "class": "center",
            "width": "3%",
            "sortable": false,
            "data": 'id',
            "mRender": function (data, display, row) {
                return '<input type="checkbox" value=' + data + ' class="ace" data-sort="' + row.sort + '"/><span class="lbl"></span>';
            }
        },
            {"title": "ID", "class": "center", "width": "4%", "sortable": false, "data": "id"},
            {"title": "标签名称", "class": "center J-label-name", "width": "4%", "sortable": false, "data": "name"},
            {"title": "标签详情", "class": "center J-label-details", "width": "4%", "sortable": false, "data": "details"},
            {"title": "文章数", "class": "center J-post-count", "width": "4%", "sortable": false, "data": "postsCount"},
            {
                "title": "图标",
                "class": "center ",
                "width": "4%",
                "sortable": false,
                "data": "labelImgUrl",
                "mRender": function (data, display, row) {
                    return '<img src="' + data + '" width="20" height="20"/>';
                }
            },
            {
                "title": "状态",
                "class": "center",
                "width": "5%",
                "sortable": false,
                "data": 'disable',
                "mRender": function (data, display, row) {
                    return (row.disable === true) ? "已禁用" : "已启用";
                }
            }
        ];

        labelTable = initTables("labelTable", basePath + "/bbs/label", objData, true, true, 0, null, null, function (data) {
        });

        labelForm.on("change", "#imgPath_file", function () {
            uploadImg(this, 'imgPath_file', $('#add_imgPath'));
        });
        editLabelForm.on("change", "#J_edit_imgPath_file", function () {
            uploadImg(this, 'J_edit_imgPath_file', $('#J_edit_add_imgPath'));
        });

        /**
         * 删除
         */
        $('.delete_bx').on('click', function (e) {
            e.preventDefault();
            var ids = [];
            var valid = true;
            $("input[type=checkbox]:checked").each(function (index, e) {
                ids[index] = $(e).val();
                var count = $(e).parent().parent().find('.J-post-count').text();
                console.log(count);
                if (count > 0 && valid) {
                    alertInfo("不能删除有文章关联的标签");
                    valid = false;
                }
            });
            if (!valid) {
                return false;
            }
            if (ids.length > 0) {
                confirmInfo('确认删除标签？', function () {
                    $.ajax({
                        'url': basePath + "/bbs/label/delete",
                        'method': 'POST',
                        'data': {'ids': ids.join(',')},
                        'dataType': 'json',
                        'success': function (resp) {
                            freshTable(labelTable);
                            console.log(resp);
                        }
                    })
                });
            } else {
                alertInfo("至少选择一个标签");
            }
        });

        /**
         * 状态变更
         */
        $('.status_bx').on('click', function (e) {
            var ids = [];
            $("input[type=checkbox]:checked").each(function (index, e) {
                ids[index] = $(this).val();
            });
            if (ids.length > 0) {
                confirmInfo('确认进行此操作？', function () {
                    $.ajax({
                        'url': basePath + "/bbs/label/changeStatus",
                        'method': 'POST',
                        'data': {"ids": ids.join(',')},
                        'dataType': 'json',
                        'success': function (resp) {
                            freshTable(labelTable);
                            console.log(resp);
                        }
                    })
                })
            } else {
                alertInfo("至少选择一条数据");
            }
        });

        /**
         * 新增弹框
         */
        $(".add_bx").on('click', function (e) {
            createImageUpload($('.uploadImg'));
            labelForm.resetForm();
            var dialog = openDialog("addLabelDialog", "addLabel", "新增标签", 580, 500, true, "确定", function () {
                if (labelForm.valid()) {
                    mask();
                    labelForm.attr("action", basePath + "/bbs/label/create");
                    labelForm.ajaxSubmit(function (data) {
                        try {
                            data = jQuery.parseJSON(jQuery(data).text());
                        } catch (e) {
                        }
                        if (data) {
                            dialog.dialog("close");
                            location.reload();
                        } else {
                            alertInfo('保存失败');
                        }
                    });
                }
            });
        });

        $('.edit_bx').on('click', function (e) {
            editLabelForm.resetForm();
            var ids = [];
            var checkedRow;
            $("input[type=checkbox]:checked").each(function (index, e) {
                ids[index] = $(e).val();
                checkedRow = $(e);
            });
            if (ids.length === 0) {
                alertInfo('必须选择一条数据');
                return;
            }
            if (ids.length > 1) {
                alertInfo('最多选择一条数据');
                return;
            }
            var tr = checkedRow.parent().parent();
            var imgSrc = tr.find('img').prop('src');
            $('#J_edit_id').val(ids[0]);
            $('#J_edit_name').val(tr.find('.J-label-name').text());
            $('#J_edit_details').val(tr.find('.J-label-details').text());
            $('#J_edit_sort').val(checkedRow.data('sort'));
            $('#J_edit_add_imgPath').val(imgSrc);
            var dialog = openDialog("editLabelDialog", "editLabel", "编辑标签", 580, 500, true, "确定", function () {
                if ($('#editLabelDialog-form').valid()) {
                    mask();
                    editLabelForm.attr("action", basePath + "/bbs/label/update");
                    editLabelForm.ajaxSubmit(function (data) {
                        try {
                            data = jQuery.parseJSON(jQuery(data).text());
                        } catch (e) {
                        }
                        if (data) {
                            dialog.dialog("close");
                            location.reload();
                        } else {
                            alertInfo("提交失败");
                        }
                    });
                }
            });
            reviewImage("J_edit_imgPath_file", imgSrc);
        });

        editLabelForm.validate({
            messages: {
                name: {
                    required: "请输入标签名称"
                },
                details: {
                    required: "请填写标签详情！"
                },
                sort: {
                    required: "请输入排序值！"
                },
                labelImgUrl: {
                    required: "请上传图片!"
                }
            }
        });

        labelForm.validate({
            messages: {
                name: {
                    required: "请输入标签名称"
                },
                details: {
                    required: "请填写标签详情！"
                },
                sort: {
                    required: "请输入排序值！"
                },
                labelImgUrl: {
                    required: "请上传图片!"
                }
            }
        });

        createImageUpload($('.uploadImg'));
    }
);


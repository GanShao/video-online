<template>
  <div>
    <button type="button" v-on:click="selectFille()" class="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-edit"></i>
      {{text}}
    </button>
    <input class="hidden" type="file" ref="file" v-on:change="uploadFille()" v-bind:id="inputId+'-input'">
  </div>
</template>

<script>
    export default {
        name: 'file',
        props: {
            text: {
                default: "上传文件",
            },
            inputId: {
                default: "file-upload",
            },
            suffixs: {
                default: [],
            },
            use: {
                default: "",
            },
            afterUpload: {
                type: Function,
                default: null
            }
        },
        data: function () {
            return {}
        },
        methods: {
            /**
             * 头像上传方法
             */
            uploadFille() {
                let _this = this;
                //vue 中使用 new window.FormData() 创建 FormData 对象
                let formData = new window.FormData();
                //使用$refs获取vue组件
                let file = _this.$refs.file.files[0];//相当于document.querySelector('#file-upload-input').files[0]

                // 判断文件格式
                let suffixs = _this.suffixs;
                let fileName = file.name;
                let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
                let validateSuffix = false;
                for (let i = 0; i < suffixs.length; i++) {
                    if (suffixs[i].toLowerCase() === suffix) {
                        validateSuffix = true;
                        break;
                    }
                }
                if (!validateSuffix) {
                    Toast.warning("文件格式不正确！只支持上传：" + suffixs.join(","));
                    $("#" + _this.inputId + "-input").val("");
                    return;
                }

                //key : "file" 必须要与后端controller参数名一样
                formData.append('file', file);
                formData.append('use', _this.use);
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/oss-simple', formData).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    console.log("上传文件成功！" + resp);
                    _this.afterUpload(resp);
                    //没次上传完将控件里面的值清空
                    $("#" + _this.inputId + "-input").val("");
                })
            },

            /**
             * 上传头像按钮
             */
            selectFille() {
                let _this = this;
                $("#" + _this.inputId + "-input").trigger("click");
            }
        }
    }
</script>


<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{course.name}}</router-link>
    </h4>
    <hr>
    <p>
      <router-link to="/business/course" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-arrow-left blue"></i>
        返回课程
      </router-link>
      &nbsp;
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit blue"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="query(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh blue"></i>
        刷新
      </button>
    </p>
    <pagination ref="pagination" v-bind:list="query" v-bind:itemCount="8"></pagination>

    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>名称</th>
        <th>课程ID</th>
        <th>操作</th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="chapter in chapters">
        <td>{{chapter.id}}</td>
        <td>{{chapter.name}}</td>
        <td>{{chapter.courseId}}</td>

        <td>
          <div class=" btn-group">
            <button v-on:click="toSection(course)" class="btn btn-white btn-xs btn-info btn-round">
              小节
            </button>&nbsp;
            <button v-on:click="edit(chapter)" class="btn btn-white btn-xs btn-info btn-round">
              编辑
            </button>&nbsp;
            <button v-on:click="del(chapter.id)" class="btn btn-white btn-xs btn-info btn-round">
              删除
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>

    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
              aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="chapter.name" class="form-control" placeholder="名称">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{course.name}}</p>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
  </div>
</template>

<script>
    import Pagination from "../../components/pagination";

    export default {
        components: {Pagination},
        name: "chapter",
        data: function () {
            return {
                chapter: {},
                chapters: [],
                course: {},
            }
        },
        mounted: function () {
            let _this = this;
            _this.$refs.pagination.size = 5;
            let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
            if (Tool.isEmpty(course)) {
                _this.$router.push("welcome")
            }
            _this.course = course;
            _this.query(1);
            // sidebar激活样式方法一
            this.$parent.activeSidebar("business-course-sidebar");
        },
        methods: {
            add() {
                let _this = this;
                _this.chapter = {};
                //禁止点空白的地方关闭
                $(".modal").modal({backdrop: "static"});
                //打开模态框
                $("#form-modal").modal("show");
            },

            edit(chapter) {
                let _this = this;
                _this.chapter = $.extend({}, chapter);
                //禁止点空白的地方关闭
                $(".modal").modal({backdrop: "static"});
                //打开模态框
                $("#form-modal").modal("show");
            },

            query(page) {
                let _this = this;
                Loading.show();
                //post请求有两种方式：1、表单方式。2、json流的方式
                //jQuery默认使用表单的方式，但是vue,angular使用流的方式
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/chapter/query", {
                    page: page,
                    size: _this.$refs.pagination.size,
                    courseId: _this.course.id
                }).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    _this.chapters = resp.content.list;
                    _this.$refs.pagination.render(page, resp.content.total);
                })
            },

            save() {
                let _this = this;
                // 保存校验
                if (!Validator.require(_this.chapter.name, "名称")
                    || !Validator.length(_this.chapter.courseId, "课程ID", 1, 8)) {
                    return;
                }
                _this.chapter.courseId = _this.course.id;
                Loading.show();
                _this.$ajax.post(process.env.VUE_APP_SERVER + "/business/admin/chapter/save", _this.chapter).then((response) => {
                    Loading.hide();
                    let resp = response.data;
                    //如果保存成功，关闭模态框，并刷新
                    if (resp.success) {
                        $("#form-modal").modal("hide");
                        _this.query(1);
                        Toast.success("保存成功！");
                    } else {
                        Toast.warning(resp.message);
                    }
                })
            },

            /**
             * 点击删除
             * @param id
             */
            del(id) {
                let _this = this;
                Confirm.show("删除大章后不可恢复，确认删除？", function () {
                    Loading.show();
                    _this.$ajax.delete(process.env.VUE_APP_SERVER + "/business/admin/chapter/delete/" + id).then((response) => {
                        Loading.hide();
                        let resp = response.data;
                        if (resp.success) {
                            _this.query(1);
                            Toast.success("删除成功！");
                        }
                    })
                });
            },

            /**
             * 点击小节
             * @param id
             */
            toSection(chapter) {
                let _this = this;
                SessionStorage.set(SESSION_KEY_CHAPTER, chapter);
                _this.$router.push("/business/section");
            },
        }
    }
</script>

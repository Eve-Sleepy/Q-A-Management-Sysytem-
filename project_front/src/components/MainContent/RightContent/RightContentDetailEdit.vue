<template>
    <div class="rightContentDetailEdit" v-if="document.title!=null">
        <positionhit
                v-once :productId="document.product.id"
                :productName="document.product.name"></positionhit>
        <div class="editarea">
            <!-- 输入标题 -->
            <div class="title_input">
                <el-input type="textarea" autosize placeholder="请输入标题"
                          v-model="document.title" maxlength="40" show-word-limit>
                </el-input>
                <el-button type="success" size="small" @click="update">
                    更新
                </el-button>
            </div>
            <!-- 选择产品 -->
            <span>产品：</span>
            <el-select size="small" v-model="document.product.id" placeholder="请选择">
                <el-option
                        v-for="item in products"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                </el-option>
            </el-select>
            <!-- 选择版本 -->
            <div class="edition_input">
                <span>版本：</span>
                <el-input size="small" v-model="document.edition"
                          placeholder="请输入版本">
                </el-input>
            </div>
            <!-- 选择部门 -->
            <div class="dept_select">
                <span>部门：</span>
                <el-select size="small" v-model="document.deptBelong" placeholder="请选择">
                    <el-option
                            v-for="item in dept"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </div>
            <vue-ueditor-wrap v-model="document.content" :config="myConfig"></vue-ueditor-wrap>
        </div>
    </div>
</template>

<script>
    import positionhit from './Position'
    import jwtDecode from 'jwt-decode'

    //引入Ueditor
    import VueUeditorWrap from '../../../../node_modules/vue-ueditor-wrap'

    export default {
        name: 'rightContentDetailEdit',
        props: ['productId', 'productName'],
        components: {
            "positionhit": positionhit,
            "vue-ueditor-wrap": VueUeditorWrap
        },
        data() {
            return {
                decode: jwtDecode(window.localStorage.Authorization),
                isAdmin: null,
                userId: null,
                tagInputVisible: false,
                tagInputValue: null,
                docId: null,
                document: {},
                products: [],
                dept: [
                    {
                        id: 0,
                        name: "设计"
                    },
                    {
                        id: 1,
                        name: "开发"
                    },
                    {
                        id: 2,
                        name: "实施"
                    },
                    {
                        id: 3,
                        name: "测试"
                    }
                ],
                //Ueditor配置
                myConfig: {
                    // 如果需要上传功能,找后端小伙伴要服务器接口地址
                    serverUrl: 'http://116.62.162.235:5000/ueditor/ueditorConfig',
                    // 你的UEditor资源存放的路径,相对于打包后的index.html
                    UEDITOR_HOME_URL: '../../../../static/ueditor/',
                    // 编辑器不自动被内容撑高
                    autoHeightEnabled: false,
                    // 工具栏是否可以浮动
                    autoFloatEnabled: false,
                    // 初始容器高度
                    initialFrameHeight: 340,
                    // 初始容器宽度
                    initialFrameWidth: '100%',
                    // 关闭自动保存
                    enableAutoSave: true
                },
            }
        },
        methods: {
            // 删除标签
            handleClose(tag) {
                this.document.tagGroup.splice(this.document.tagGroup.indexOf(tag), 1);
            },

            showInput() {
                this.tagInputVisible = true;
                this.$nextTick(_ => {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            },
            //创建新标签
            handleInputConfirm() {
                let inputValue = this.tagInputValue;
                if (inputValue) {
                    //去除重复标签
                    for (let i = 0; i < this.document.tagGroup.length; ++i) {
                        if (inputValue === this.document.tagGroup[i]) {
                            alert("重复标签");
                            this.tagInputVisible = false;
                            this.tagInputValue = null;
                            return;
                        }
                    }
                    this.document.tagGroup.push(inputValue);
                }
                this.tagInputVisible = false;
                this.tagInputValue = null;
            },
            //保存 上传文档内容
            update() {
                //对上传的数据进行处理
                let newDocument = this.document;
                //删掉author字段
                newDocument.productId = this.document.product.id;
                //新增userId
                newDocument.authorId = this.userId;
                //新增docId
                newDocument.docId = this.docId;
                //post
                this.$axios({
                    method: 'post',
                    url: "/api/doc/update",
                    headers: {'Authorization': window.localStorage['Authorization']},
                    data: newDocument
                }).then(res => {
                    if (res.data.status === 200) {
                        //提示修改成功
                        this.$message({
                            message: '修改成功',
                            type: 'success'
                        });
                        //跳转到文章详情页
                        this.$router.push({name: "detail", params: {docId: this.docId}});
                    } else {
                        //提示
                        this.$message.error('未知错误！');
                    }
                })
                    .catch(error => {
                    });
            }
        },
        created() {
            this.userId = Number(this.decode.userId)
            this.isAdmin = Number(this.decode.isAdmin)
            //获取产品列表
            this.$axios({
                method: 'get',
                url: "/api/product/dropDownList",
                headers: {'Authorization': window.localStorage['Authorization']}
            }).then((res) => {
                if (res.data.status === 200) {
                    this.products = res.data.data;
                }
            });
            //获取文档内容
            this.docId = Number(this.$route.params.docId);

            this.$axios({
                method: 'get',
                url: "/api/doc/select",
                headers: {'Authorization': window.localStorage['Authorization']},
                params: {docId: this.docId}
            }).then((res) => {
                if (res.data.status === 200) {
                    this.document = res.data.data;
                }
            });
        }
    }
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    /* 白色背景框 */
    .editarea {
        position: relative;
        width: 924px;
        background-color: white;
        border-radius: 10px;
        margin-top: 24px;
        margin-bottom: 24px;
        padding: 24px 48px;
    }

    .editarea .title_input,
    .editarea .edition_input,
    .editarea > .el-select,
    .editarea > .dept_select {
        margin-bottom: 16px;
    }

    /* 标签的下边距 */
    .editarea .tags_input {
        margin-bottom: 20px;
    }

    /* ---------title ---------*/
    .editarea .title_input {
        width: 100%;
    }

    .editarea .title_input > .el-textarea {
        max-width: calc(100% - 82px - 64px);
    }

    .editarea .title_input > .el-input > span {
        color: #909399;
        font-size: 12px;
    }

    /* ------保存按钮------ */
    .editarea .title_input > .el-button {
        background-color: #41B298;
        border: none;
        float: right;
    }

    .editarea .title_input > .el-button:hover {
        opacity: 0.8;
    }

    .editarea .title_input > .el-button > span {
        color: white;

    }

    /* 版本、部门 */
    .editarea .edition_input, .dept_select {
        display: inline-block;
    }

    .editarea .edition_input > .el-input {
        width: 100px;
    }

    /* 产品、版本 */
    .editarea > .el-select, .edition_input {
        margin-right: 24px;
    }

    /* 部门 */
    .dept_select > .el-select {
        width: 100px;
    }

    /* ---标签--- */
    .tags_input .el-tag {
        border-radius: 100px;
    }

    .el-tag + .el-tag {
        margin-left: 10px;
    }

    .button-new-tag {
        margin-left: 10px;
        height: 32px;
        line-height: 30px;
        padding-top: 0;
        padding-bottom: 0;
        border: none;
    }

    .input-new-tag {
        width: 90px;
        margin-left: 10px;
        vertical-align: bottom;
    }

    .button-new-tag > span {
        font-size: 12px;
        color: #666;
    }

    @media screen and (max-width: 1184px) {
        .editarea {
            width: calc(924px - 1184px + 100vw);
        }
    }

    @media screen and (max-width: 1096px) {
        .editarea {
            width: calc(924px - 1184px + 100vw);
        }
    }

    @media screen and (max-width: 768px) {
        .editarea {
            width: calc(924px - 1184px + 100vw + 180px) !important;
        }
    }
</style>

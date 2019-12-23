<template>
    <div class="documentlist">
        <div class="withoutData" v-show="!isHaveData">
            <img src="../../build/img/withoutData.png">
            <p>您的私信箱空空如也</p>
        </div>
        <ul>

            <li v-for="msg in msgList.arr" class="documentarea" @click='to_detail(msg.docId,msg.msgId)'>
                <div class="themecolor" v-bind:style="{backgroundColor:msg.product.color}"></div>
                <div class="icon_btn_div">
                </div>

                <div class="documentname">
                    <p> {{msg.title}}</p>
                </div>
                <div class="icon_btn_div">
                    <el-tag type="danger" v-if="msg.state === 0">未读</el-tag>
                    <el-tag type="success" v-else>已读</el-tag>
                    <img class="icon_edit" src="../../build/img/icon_delete2@2x.png" @click.stop='to_delete(msg.msgId)'>
                </div>

                <div class="documentdetail">{{msg.senderName}} /
                    <p>{{msg.product.name}}{{msg.edition}}</p>
                    &nbsp&nbsp{{msg.sendTime}}&nbsp
                    <span v-if="msg.deptBelong==0">/设计</span>
                    <span v-else-if="msg.deptBelong==1">/开发</span>
                    <span v-else-if="msg.deptBelong==2">/实施</span>
                    <span v-else-if="msg.deptBelong==3">/测试</span> 部门
                </div>

            </li>


        </ul>
        <div class="bgforpage" v-show="isHaveData">
            <el-pagination
                    @current-change="handleCurrentChange"
                    :current-page.sync="search.currentPage"
                    :page-size="search.perPage"
                    layout="prev, pager, next, jumper"
                    :total="msgList.total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import jwtDecode from 'jwt-decode'

    export default {
        name: 'msglist',
        inject: ['reload'],
        data() {
            return {
                decode: jwtDecode(window.localStorage.Authorization),
                search: {
                    "userId": null,
                    "perPage": 5,
                    "currentPage": 1
                },
                msgList: {},
                isHaveData: true
            }
        },
        methods: {
            handleCurrentChange(val) {
                this.to_search();
            },
            to_search() {
                // 获得私信列表
                this.$axios({
                    method: 'post',
                    url: "/api/msg/list",
                    headers: {'Authorization': window.localStorage['Authorization']},
                    data: this.search
                }).then(res => {
                    if (res.data.status === 200) {
                        this.msgList = res.data.data;
                        this.isHaveData = true;
                    } else if (res.data.status === 501) {
                        this.msgList = {};
                        this.isHaveData = false;
                    }
                }).catch(err => {
                })
            },
            to_delete: function (msgId) {
                //删除私信
                this.$confirm('您确定要删除该私信吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios({
                        method: 'get',
                        url: "/api/msg/delete",
                        headers: {'Authorization': window.localStorage['Authorization']},
                        params: {'msgId': msgId}
                    }).then(res => {
                        if (res.data.status === 200) {
                            this.$message({
                                message: '删除私信成功',
                                type: 'success'
                            });
                            this.reload()
                        } else if (res.data.status === 501) {
                        }
                    }).catch(err => {
                    })
                })

            },
            to_detail: function (docId, msgId) {
                //跳转到文章详情页
                this.$axios({
                    method: 'get',
                    url: "/api/msg/check",
                    headers: {'Authorization': window.localStorage['Authorization']},
                    params: {'msgId': msgId}
                }).then(res => {
                    if (res.data.status === 200) {
                        this.$router.push({name: "detail", params: {docId: docId}});
                    } else if (res.data.status === 501) {
                    }
                }).catch(err => {
                })
            }
        },
        created() {
            this.search.userId = Number(this.decode.userId)
            this.to_search();
        }
    }
</script>

<style scoped>
    .item {
        margin-top: 10px;
        margin-right: 40px;
    }

    .documentlist {
        width: 924px;
        margin: 0 auto;
    }

    .documentarea {
        width: 100%;
        height: auto;
        background: rgba(255, 255, 255, 1);
        border-radius: 10px;
        border: 1px solid rgba(245, 245, 245, 1);
        margin-top: 24px;
        position: relative;
        padding-top: 14px;
        padding-bottom: 11px;
        padding-left: 18px;
        cursor: pointer;
    }

    .themecolor {
        display: inline-block;
        position: absolute;
        top: 0;
        left: 0;
        width: 6px;
        height: 100%;
        opacity: 0.5;
        border-radius: 10px;
        border: none;
    }

    /* ---------title ---------*/
    .documentname > p {
        display: inline-block;
        position: relative;
        margin: 0;
        margin-bottom: 10px;
        margin-right: 8px;
        color: #333333;

        max-width: 63%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        vertical-align: bottom;
    }

    /* ---标签--- */
    .tagsfordoc {
        display: inline-block;
        vertical-align: bottom;
    }

    .tagsfordoc > .badge {
        font-size: 10px;
        font-weight: normal;
        padding: 1px 6px;
        background-color: #B3B3B3;
        margin-right: 8px;
        margin-bottom: 10px;
        line-height: normal;
        letter-spacing: normal;
    }

    /* ---------下载和编辑按钮--------- */
    .documentlist .icon_btn_div {
        position: absolute;
        right: 11px;
        top: -16px;
        height: 100%;
    }

    .documentlist .icon_edit {
        width: 38px;
        height: 38px;
        margin-top: 30px;
        margin-left: 20px;
    }

    .documentlist .icon_edit_btn {
        float: right;
        padding: 25px 9px;
        background: none;

    }

    /* ---------文档的详细信息 ---------*/
    .documentdetail, .documentdetail p, .documentdetail span {
        font-size: 12px;
        color: #999999;
        letter-spacing: 0.3px;
    }

    .documentdetail p {
        display: inline-block;
        margin: 0;
        max-width: 19%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        vertical-align: bottom;

    }

    /* --------分页-------- */
    .el-pager > li.active, .el-pager > li:hover {
        color: #41B298;
    }

    .bgforpage {
        margin-top: 32px;
        padding: 2px 6px;
        background-color: white;
        border-radius: 6px;
        float: right;
    }

    .bgforpage > .el-pagination {

    }

    /* 无查询数据 */
    .withoutData {
        margin-top: 16px;
        background-color: white;
    }

    .withoutData > img {
        padding-top: 24px;
        width: 64px;
        margin-left: 50%;
        transform: translateX(-50%);
    }

    .withoutData > p {
        text-align: center;
        padding-top: 6px;
        font-size: 13px;
        padding-bottom: 24px;
    }


    @media screen and (max-width: 1184px) {
        .documentlist {
            width: calc(924px - 1184px + 100vw);
        }
    }

    @media screen and (max-width: 1096px) {
        .documentlist {
            width: calc(924px - 1184px + 100vw);
        }

        .documentname > p {
            max-width: calc(100% - 100px);
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            vertical-align: bottom;
        }

        .documentlist .icon_download_btn, .icon_edit_btn {
            height: -webkit-fill-available;
        }
    }
</style>

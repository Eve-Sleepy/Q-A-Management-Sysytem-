<template>
    <div class="documentlist">
        <!-- 无查询数据时显示 -->
        <div class="withoutData" v-show="!isHaveData">
            <img src="../../build/img/withoutData.png">
            <p>没有找到符合条件的结果</p>
        </div>

        <ul>
            <li v-for="document in documentList.arr" class="documentarea" @click='to_detail(document.docId)'>
                <div class="themecolor" v-bind:style="{backgroundColor:document.product.color}"></div>
                <div class="icon_btn_div">
                    <div type="" class="btn icon_edit_btn" v-if="showEdit||parseInt(idd) === document.author.id"
                         @click.stop="to_edit(document.docId)"><img class="icon_edit"
                                                                    src="../../build/img/icon_edit@2x.png">
                    </div>
                </div>
                <div class="documentname">
                    <p>{{document.title}}</p>
                    <div class="tagsfordoc">
                        <span v-for="tag in document.tagGroup" class="badge">{{tag}}</span>
                    </div>
                </div>
                <div class="documentdetail">{{document.author.realname}} /
                    <p>{{document.product.name}} {{document.product.edition}}</p>
                    &nbsp&nbsp{{document.operationTime}}&nbsp
                    <span v-if="document.operation">上传了文档</span>
                    <span v-else>更新了文档</span>
                </div>
            </li>
        </ul>
        <div class="bgforpage" v-show="isHaveData">
            <el-pagination
                    @current-change="handleCurrentChange"
                    :current-page.sync="search.currentPage"
                    :page-size="search.perPage"
                    layout="prev, pager, next, jumper"
                    :total="documentList.total">
            </el-pagination>
        </div>
    </div>
</template>

<script>

    import jwtDecode from 'jwt-decode'

    export default {
        name: 'documentlist',
        props: ['productId', 'keyword', 'sortway', 'authorId', 'tagGroup'],
        inject: ['reload'],
        methods: {
            showPrise() {
                this.idd = parseInt(this.decode.userId)
                const flag = this.decode.isAdmin
                if (flag === '1')
                    this.showEdit = true
            },
            handleCurrentChange(val) {
                this.to_search();
            },
            to_detail: function (id) {
                //跳转到文章详情页
                this.$router.push({name: "detail", params: {docId: id}});
            },
            to_edit: function (id) {
                //跳转并向编辑页面发送docId
                this.$router.push({name: "edit", params: {docId: id}});
            },
            to_search() {
                //传递搜索、筛选信息，返回文档列表
                //路由获取产品id
                if (Number(this.$route.query.productId))
                    this.search.productId = Number(this.$route.query.productId);
                this.search.deptBelong = Number(this.$route.query.deptBelong);
                this.$axios({
                    method: 'post',
                    url: "/api/doc/list",
                    headers: {'Authorization': window.localStorage['Authorization']},
                    data: this.search
                }).then(res => {
                    if (res.data.status === 200) {
                        this.documentList = res.data.data;
                        //有数据的情况下，分页栏显示
                        this.isHaveData = true;
                    } else if (res.data.status === 201) {
                        this.documentList = {};
                        this.isHaveData = false;
                    }
                })
                    .catch(error => {
                    });

            }
        },
        data() {
            return {
                decode: jwtDecode(window.localStorage.Authorization),
                documentList: {},
                search: {
                    keyword: "",
                    sortway: 0,
                    productId: null,
                    authorId: null,
                    currentPage: 1,
                    perPage: 5,
                    tagGroup: [],
                    deptBelong: null
                },
                isHaveData: true,
                showEdit: false,
                idd: 0
            }
        },
        created() {
            this.to_search();
            this.showPrise();
        },
        watch: {
            '$route'(to, from) {
                this.reload();
            },
            //监听props内容变化
            //搜索关键字
            keyword(val) {
                this.search.keyword = val;
                this.search.currentPage = 1;
                this.to_search();
            },
            //排序
            sortway(val) {
                this.search.sortway = val;
                this.search.currentPage = 1;
                this.to_search();
            },
            //产品筛选
            productId(val) {
                this.search.productId = val;
                this.search.currentPage = 1;
                this.to_search();
            },
            //作者筛选
            authorId(val) {
                this.search.authorId = val;
                this.search.currentPage = 1;
                this.to_search();
            },
            tagGroup(val) {
                this.search.tagGroup = val;
                this.search.currentPage = 1;
                this.to_search();
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
    .documentlist {
        width: 924px;
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
        top: 0;
        height: 100%;
    }

    .documentlist .icon_download, .icon_edit {
        width: 18px;
        height: 18px;
    }

    .documentlist .icon_download_btn, .icon_edit_btn {
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

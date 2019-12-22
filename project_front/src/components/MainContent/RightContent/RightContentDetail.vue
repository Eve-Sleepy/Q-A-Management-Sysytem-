<template>
    <div class="rightContentDetail" v-if="document.title">
        <positionhit
                :productId="document.product.id"
                :productName="document.product.name"></positionhit>
        <div class="detailarea" >
            <div class="icon_btn_div2" >
                <img @click="to_edit" v-if="showOpts||document.authorId===parseInt(idd)" class="icon_edit2" src="../../../../build/img/icon_edit2@2x.png">
                <img @click="open3" class="icon_download2" src="../../../../build/img/icon_download2@2x.png">
                <img @click="to_delete" v-if="showOpts||document.authorId===parseInt(idd)" class="icon_delete2" src="../../../../build/img/icon_delete2@2x.png">
            </div>
            <div class="documentname">
                <p >{{document.title}}</p>
                <div class="tagsfordoc">
                    <span v-for="tag in document.tagGroup"class="badge">{{tag.name}}</span>
                </div>
            </div>
            <div class="documentdetail">{{document.authorName}} /
                <p>{{document.product.name}}&nbsp{{document.edition}}</p>
                <span v-if="document.deptBelong===0">/设计</span>
                <span v-else-if="document.deptBelong === 1">/开发</span>
                <span v-else-if="document.deptBelong===2">/实施</span>
                <span v-else-if="document.deptBelong===3">/测试</span>
                &nbsp&nbsp{{document.operationTime}}&nbsp
                <span v-if="document.operation===1">更新了文档</span>
                <span v-else-if="document.operation===0">上传了文档</span>
            </div>
            <el-divider></el-divider>
            <div v-html="document.content"></div>
            <el-divider v-if="isShowReply" ></el-divider>
            <div v-if="isShowReply">评论</div>
            <ul id="reply">
                <li class="documentareas" v-for="(item, index) in replyList" :key="index">
                    <div class="themecolor" style="background-color: rgb(34, 51, 255);"></div>
                    <div class="icon_btn_div"></div>
                    <div class="documentdetail">
                        <div class="replyContent">
                            <span style="font-weight: bold;font-size: 16px">{{item.replierName}}: </span>&nbsp
                            {{item.replyContent}} <br>
                            <div style="margin-top: 10px"> {{item.replyTime}}</div>
                        </div>&nbsp;
                        <img v-if="showOpts||item.replierId===parseInt(idd)"
                             @click="to_deleteReplyId(item.replyId)" class="icon_edit2 icon_ddlete"
                             src="../../../../build/img/icon_delete2@2x.png">
                    </div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    import positionhit from './Position'
    import documentList from '../../DocumentList'
    import siftdoc from '../../SiftDoc'
    import jwtDecode from 'jwt-decode'
    export default {
        name: 'rightContentDetail',
        props:['productId','productName'],
        inject:['reload'],
        components:{
            "positionhit":positionhit,
            "documentList":documentList,
            "siftdoc":siftdoc
        },
        methods:{
            showOp(){
                this.idd = this.decode.userId
                const flag = this.decode.isAdmin
                if(flag === '0')
                    this.showOpts = false
            },
            //前往编辑页
            to_deleteReplyId:function(replyId){
                //跳转并向编辑页面发送docId
                this.$confirm('您确定要删除该评论吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    //向数据库发送删除请求
                    this.$axios({
                        method: 'get',
                        url: "/api/reply/delete",
                        headers: {'Authorization':window.localStorage['Authorization']},
                        params:{replyId:replyId}
                    }).then((response) => {
                        if(response.data.status === 200){
                            //返回用户提示
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                            this.reload();

                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            to_edit:function(){
                console.log(this.docId);

                //跳转并向编辑页面发送docId
                this.$router.push({name:"edit",params:{docId:this.docId}});
            },

            open3() {
                this.$prompt('请输入评论内容', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({ value }) => {
                    this.$axios({
                        method: 'post',
                        url: "/api/reply/create",
                        headers: {'Authorization':window.localStorage['Authorization']},
                        data: {'docId':this.docId,'replierId':parseInt(this.decode.userId),'replyContent':value}
                    }).then(res=> {
                        console.log(res.data);
                        if(res.status === 200){
                            //提示
                            this.$message({
                                message: '评论成功',
                                type: 'success'
                            });
                            this.reload();
                        }
                    }).catch( error =>{
                            console.log(error);
                        });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消输入'
                    });
                });
            },
            //删除文档
            to_delete(){
                this.$confirm('您确定要删除该文档吗？', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    //向数据库发送删除请求
                    this.$axios({
                        method: 'get',
                        url: "/api/doc/delete",
                        headers: {'Authorization':window.localStorage['Authorization']},
                        params:{docId:this.docId}
                    }).then((response) => {
                        if(response.data.status === 200){
                            //返回用户提示
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                            //刷新当前页面,跳转到文档列表
                            this.$router.push({path:"/content",query:{productId:this.document.product.id}});
                            console.log("id:"+this.document.product.id);

                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        },
        created(){
            this.showOp();
            this.docId = Number(this.$route.params.docId);
            this.$axios({
                method: 'get',
                url: "/api/reply/list?docId="+this.docId,
                headers: {'Authorization':window.localStorage['Authorization']},
            }).then((res) => {
                if(res.data.status === 200){
                    this.replyList=res.data.data.arr
                    if(this.replyList.length !== 0){
                        this.isShowReply = true
                    }else{
                        this.isShowReply = false
                    }
                }else if(res.data.status === 501){
                    this.replyList=[]
                    this.isShowReply = false
                }

            });
            this.$axios({
                method: 'get',
                url: "/api/doc/select",
                headers: {'Authorization':window.localStorage['Authorization']},
                params: {docId:this.docId}
            }).then((response) => {
                if(response.status === 200){
                    this.document = response.data.data;
                    console.log(response.data.data);
                }
            });
        },
        data(){
            return{
                decode: jwtDecode(window.localStorage.Authorization),
                docId:'',
                document:{},
                replyList:[],
                isShowReply: false,
                showOpts: true
            }
        }
    }
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

    /* 白色背景框 */
    .documentareas{
        width: 100%;
        height: auto;
        background: rgba(255,255,255,1);
        border-radius: 10px;
        border: 1px solid rgba(245,245,245,1);
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
    .detailarea{
        position: relative;
        width: 924px;
        background-color: white;
        border-radius:10px;
        margin-top: 24px;
        margin-bottom: 24px;
        padding:24px;
    }
    /* 按钮 */
    .icon_download2,.icon_edit2,.icon_delete2{
        width: 30px;
        margin-left: 16px;
        cursor: pointer;
    }
    .icon_btn_div2{
        float: right;
    }

    /* 删除的icon放右边 */
    .icon_ddlete{
        float: right;
        margin-right: 16px;
        position: relative;
        bottom: 33px;
    }

    .icon_download2:hover,.icon_edit2:hover,.icon_delete2:hover{
        opacity:0.7;
    }
    /* ---------title ---------*/
    .detailarea .documentname>p{
        position: relative;
        margin: 0;
        margin-bottom: 10px;
        color: #333333;
        max-width:calc(100% - 186px);
        overflow: hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
        vertical-align: bottom;
        font-size: 16px;
        font-weight: bold;
    }
    /* ---标签--- */
    .detailarea .tagsfordoc{
        display: inline-block;
        display: block;
        max-width: 574px;
        white-space: nowrap;
        vertical-align: bottom;
    }
    .detailarea .tagsfordoc>.badge{
        font-size: 10px;
        font-weight: normal;
        padding: 1px 6px;
        background-color: #B3B3B3;
        margin-right: 8px;
        margin-left: 0px;
        line-height: normal;
        letter-spacing: normal;
        margin-bottom: 10px;
    }
    /* ---------文档的详细信息 ---------*/
    .detailarea .documentdetail{
        margin-left:0;
    }
    .documentdetail,.documentdetail p,.documentdetail span{
        font-size: 12px;
        color: #999999;
        letter-spacing: 0.3px;
    }
    .replyContent {
        width: 95%;
        margin-right: 0;
    }
    .documentdetail p{
        display: inline-block;
        margin: 0;
        max-width:40%;
        overflow: hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
        vertical-align: bottom;

    }
    @media screen and (max-width:1184px) {
        .detailarea{
            width: calc(924px - 1184px + 100vw);
        }
    }
    @media screen and (max-width: 1096px){
        .detailarea{
            width: calc(924px - 1184px + 100vw);
        }
    }
    @media screen and (max-width: 768px){
        .detailarea{
            width: calc(924px - 1184px + 100vw + 180px) !important;
        }
    }
</style>

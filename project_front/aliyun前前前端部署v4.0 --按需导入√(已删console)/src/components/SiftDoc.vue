
<template>
    <div class="siftdoc">
        <span class="to_all" @click="to_all">恢复默认</span>
        <!-- 最近更新 -->
        <el-dropdown placement="bottom" >
        <span class="el-dropdown-link">
            {{selected.time.name}}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
            <el-dropdown-menu slot="dropdown" >
                <el-dropdown-item class="dropdown_color" v-for="timetype in selected.time.sift"
                                  :key="timetype.id"  @click.native="sift_time(timetype.type,timetype.id)">
                    {{timetype.type}}</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
        <!-- 产品 -->
        <el-dropdown placement="bottom" v-show="product_show">
        <span class="el-dropdown-link">
            {{selected.product.name}}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
            <el-dropdown-menu slot="dropdown" class="dropdown_scroll">
                <el-dropdown-item class="dropdown_color" v-for="product in selected.product.sift"
                                  :key="product.id" @click.native="sift_product(product.id,product.name)">
                    {{product.name}}</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
        <!-- 作者 -->
        <el-dropdown placement="bottom" >
        <span class="el-dropdown-link">
            {{selected.author.username}}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
            <el-dropdown-menu slot="dropdown" class="dropdown_scroll">
                <el-dropdown-item class="dropdown_color" v-for="author in selected.author.sift"
                                  :key="author.id" @click.native="sift_author(author.id,author.username)">
                    {{author.username}}</el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
        <!-- 搜索 -->

        <div class="navbar-form navbar-left1" v-if="searchShow" style="display: inline-block">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="搜索" v-model="keyword" @keyup.enter="search">
            </div>
            <button type="submit" class="btn "  @click="search" ><img class="icon_search_s" src="../../build/img/icon_search_s@2x.png"></button>
        </div>

    </div>
</template>

<script>
    export default {
        name: 'siftdoc',
        props:['product_show'],
        inject:['reload'],
        methods:{
            //一切恢复默认
            to_all:function () {
                this.selected.time.name="最近更新";
                this.selected.time.sortway=0;
                this.selected.product.name="产品";
                this.selected.product.id="";
                this.selected.author.username="作者";
                this.selected.author.id="";
                //向父组件传递时间排序id
                this.$emit('getSortway',0);
                //向父组件传递产品筛选id
                this.$emit('getProductId',null);
                //向父组件传递作者筛选id
                this.$emit('getAuthorId',null);
            },
            sift_time:function(type,id){
                this.selected.time.name = type;
                this.selected.time.sortway= id;
                //向父组件传递时间排序id
                this.$emit('getSortway',id);

            },
            sift_product:function(id,name){
                this.selected.product.name = name;
                this.selected.product.id= id;
                //向父组件传递产品筛选id
                this.$emit('getProductId',id);
            },
            sift_author:function(id,name){
                this.selected.author.id = id;
                this.selected.author.username = name;
                //向父组件传递作者筛选id
                this.$emit('getAuthorId',id);
            },search:function(){
                //向父组件传递搜索关键字
                this.$emit('getKeyword',this.keyword);
            }
        },
        data(){
            return{
                searchShow: true,
                keyword: '',
                productID:"",
                editorID:"",
                selected:{
                    time:{
                        name:"最近更新",
                        sortway:0,
                        show:true,
                        sift:[
                            {
                                id:0,
                                type:"最近更新"
                            },
                            {
                                id:1,
                                type:"最早更新"
                            }
                        ],
                    },
                    product:{
                        name:"产品",
                        id:"",
                        show:true,
                        sift:[
                        ]
                    },
                    author:{
                        username:"作者",
                        id:"",
                        show:true,
                        sift:[
                        ]
                    }
                }
            }
        },
        created(){

            //获取用于筛选的产品列表
            this.$axios({
                method: 'get',
                url: "/api/product/dropDownList",
                headers: {'Authorization':window.localStorage['Authorization']}
            }).then((res) => {
                if(res.status == 200){
                    this.selected.product.sift = res.data.data;
                }
            });
            //获取用于筛选的作者列表
            this.$axios({
                method: 'get',
                url: "/api/user/dropDownList",
                headers: {'Authorization':window.localStorage['Authorization']}
            }).then((res) => {
                if(res.status == 200){
                    this.selected.author.sift = res.data.data.arr;
                }
            });
        },
        mounted(){

            if(this.$route.path=="/index"){
                this.searchShow = false;
            }else if(this.$route.path.indexOf("/content")){
                this.searchShow = true;
                this.reload()
            }
        }

    }


</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .siftdoc{
        padding-top: 24px;
        margin-left: 6px;
    }
    .to_all{
        color:#333;
        font-weight: bold;
        margin-right:16px;
        cursor: pointer;
    }
    .el-dropdown-link {
        cursor: pointer;
        color: #333;
    }
    .el-icon-arrow-down {
        font-size: 12px;
    }
    .dropdown_color:hover{
        background-color:#f8f8f8;
        color: #333;
    }
    .el-dropdown{
        margin-right:24px ;
    }
    .dropdown_scroll{
        /* 避免高度过高，导致内容不可见 */
        max-height:300px;
        overflow: scroll;
    }
    .btn{
        background: white;
    }
    #appear{
        display: block;
    }
</style>



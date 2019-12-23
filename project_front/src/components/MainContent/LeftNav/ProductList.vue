<template>
    <div class="productlist_div">
        <ul class="productlist" v-for="(product,index) in productlist">

            <li role="presentation" v-bind:class="{'divideline':index!=productlist.length-1,'listactive':product.show}"

                @click="showProduct(index)">
                <div v-bind:style="{backgroundColor:product.color}"></div>
                <a v-bind:id="product.productId">{{product.name}}</a>
            </li>
            <ul class="productDetailNum" v-show="product.show">
                <li @click="to_design" :class="{'listactive':product.show&&deptBelong==0}">
                    <a>设计&nbsp<span class="badge">{{product.designNum}}</span></a></li>
                <li @click="to_code" :class="{'listactive':product.show&&deptBelong==1}">
                    <a>开发&nbsp<span class="badge">{{product.codeNum}}</span></a></li>
                <li @click="to_implement" :class="{'listactive':product.show&&deptBelong==2}">
                    <a>实施&nbsp<span class="badge">{{product.implementNum}}</span></a></li>
                <li @click="to_test" :class="{'listactive':product.show&&deptBelong==3}">
                    <a>测试&nbsp<span class="badge">{{product.testNum}}</span></a></li>
            </ul>

        </ul>
    </div>
</template>
<script>
    export default {
        name: 'productlist',
        inject: ['reload'],
        data() {
            return {
                productlist: [],
                productId: null,
                deptBelong: null
            }
        },
        methods: {
            showProduct: function (index) {
                //是否显示
                this.productlist[index].show = !this.productlist[index].show;
                //关闭其他打开的li
                for (let i = 0; i < this.productlist.length; ++i) {
                    if (i != index)
                        this.productlist[i].show = false;
                }
                //点开list时，传递对应的id
                if (this.productlist[index].show == true) {
                    //向父父组件 MainContent传递产品name
                    this.$parent.$parent.showProduct(this.productlist[index].productId, this.productlist[index].name);
                    //跳转到对应产品页面
                    this.productId = this.productlist[index].productId;
                    this.$router.push({path: "/content", query: {productId: this.productId}});
                }
            },
            to_design() {
                //跳转到对应产品\部门页面 设计
                this.$router.push({path: "/content", query: {productId: this.productId, deptBelong: 0}});
            },
            to_code() {
                //跳转到对应产品\部门页面 开发
                this.$router.push({path: "/content", query: {productId: this.productId, deptBelong: 1}});
            },
            to_implement() {
                //跳转到对应产品\部门页面 实施
                this.$router.push({path: "/content", query: {productId: this.productId, deptBelong: 2}});
            },
            to_test() {
                //跳转到对应产品\部门页面 测试
                this.$router.push({path: "/content", query: {productId: this.productId, deptBelong: 3}});
            }
        },
        created() {
            //路由获取产品id
            this.productId = Number(this.$route.query.productId);
            //路由获取deptBelong
            this.deptBelong = Number(this.$route.query.deptBelong);
            this.$axios({
                method: 'get',
                url: "/api/product/asideList",
                headers: {'Authorization': window.localStorage['Authorization']}
            }).then((res) => {
                if (res.data.status === 200) {
                    this.productlist = res.data.data.arr;
                    //增加show属性
                    for (let i = 0; i < this.productlist.length; ++i) {
                        //判断是否为点开的产品li
                        if (this.productlist[i].id === this.productId) {
                            //向父父组件 MainContent传递产品name
                            this.$parent.$parent.showProduct(this.productlist[i].id, this.productlist[i].name);
                            //设置可见
                            this.$set(this.productlist[i], 'show', true);
                        } else
                            this.$set(this.productlist[i], 'show', false);
                    }
                }
            })
                .catch(error => {
                });
        },
        watch: {
            '$route'(to, from) {
                if (this.$route.query.productId)
                    this.reload();
            }
        }
    }


</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
    /* 产品list */
    .productlist > li {
        margin-left: 16px;
        margin-right: 16px;
        padding-bottom: 16px;
        padding-top: 16px;
    }

    a {
        text-decoration: none;
    }

    .router-link-active {
        text-decoration: none;
    }

    /* 下划线 */
    .divideline {
        border-bottom: 1px solid #f8f8f8;
    }

    .productlist > li:hover, .productDetailNum > li:hover {
        background-color: #f8f8f8;
        cursor: pointer;
    }

    .listactive {
        background-color: #f8f8f8;
    }

    .productlist > li > a {
        text-decoration: none;
        color: #448ac7;
    }

    /* 主题色标记 */
    .productlist > li div {
        display: inline-block;
        width: 6px;
        height: 14px;
        opacity: 0.5;
        border-radius: 10px;
        margin-right: 8px;
        vertical-align: top;
        position: relative;
        top: 3px;
    }

    .productDetailNum {
        padding-top: 12px;
    }

    /* 产品下的list */
    .productDetailNum > li {
        margin: 0 12px 12px 38px;
    }

    .productDetailNum > li > a {
        font-size: 13px;
        color: #666666;
    }

    .productDetailNum .badge {
        background-color: #b3b3b3;
        opacity: 0.5;
        font-weight: normal;
        margin-left: 4px;
        padding: 2px 6px;
    }

</style>

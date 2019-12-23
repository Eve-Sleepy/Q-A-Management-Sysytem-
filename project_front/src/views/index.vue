<template>
    <div class="index">
        <headerview :isBtnShow="isBtnShow"></headerview>
        <search @getKeyword="getKeyword"
                @getTagGroup="getTagGroup"
                @removeTagGroup="removeTagGroup">
        </search>
        <div id="docback">
            <siftdoc
                    :product_show="product_show"
                    @getSortway="getSortway"
                    @getProductId="getProductId"
                    @getAuthorId="getAuthorId"
                    @getKeyword="getKeyword"
            ></siftdoc>

            <router-link to='/content'>
                <el-button type="info" size="small">目录</el-button>
            </router-link>
            <documentlist
                    :keyword="keyword"
                    :sortway="sortway"
                    :productId="productId"
                    :authorId="authorId"
                    :tagGroup="tagGroup"
            ></documentlist>
        </div>
    </div>
</template>

<script>
    import headerview from '../components/Header'
    import search from '../components/index/Search'
    import siftdoc from '../components/SiftDoc.vue'
    import documentlist from '../components/DocumentList.vue'

    export default {
        name: 'index',
        data() {
            return {
                product_show: true,
                isBtnShow: false,
                keyword: '',
                sortway: null,
                productId: null,
                authorId: null,
                tagGroup: []
            }
        },
        components: {
            siftdoc, search, documentlist, headerview
        },
        methods: {
            //获取搜索关键字
            getKeyword(keyword) {
                this.keyword = keyword;
            },
            //获取timeId排序方式
            getSortway(id) {
                this.sortway = id;
            },
            getProductId(id) {
                this.productId = id;
            },
            getAuthorId(id) {
                this.authorId = id;
            },
            getTagGroup(tag) {
                this.tagGroup.unshift(tag)
            },
            removeTagGroup(index) {
                this.tagGroup = this.tagGroup.filter(x => !(x === index))
            }
        }
    }
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    #docback {
        width: 1184px;
        padding-bottom: 108px;
        background-color: white;
        margin: 0 auto;
    }

    .siftdoc {
        padding-bottom: 12px;
        padding-left: 11%;
    }

    .documentlist {
        margin-left: 11%;
        width: 924px;
    }

    /* 按钮 */
    .el-button--info {
        background-color: #fafafa;
        border: none;
        float: right;
        margin-right: 130px;
        position: relative;
        top: -36px;
        color: #333;
    }

    @media screen and (max-width: 1184px) and (min-width: 944px) {
        .documentlist {
            width: 924px !important;
            margin-left: calc(130px - 592px + 50vw);
        }

        .siftdoc {
            padding-left: calc(130px - 592px + 50vw);
        }

        #docback {
            width: 100%;
        }

        .el-button--info {
            margin-right: calc(130px - 592px + 50vw);
        }

    }

    @media screen and (max-width: 944px) {
        .documentlist {
            width: calc(924px - 944px + 100vw) !important;
            margin-left: 10px;
        }

        .siftdoc {
            padding-left: 10px;
        }

        #docback {
            width: 100%;
        }

        .el-button--info {
            margin-right: 10px;
        }
    }
</style>

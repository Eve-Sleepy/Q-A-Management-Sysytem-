<template>
    <div class="rightContentList">
        <positionhit
                :productId="productId"
                :productName="productName"
                :keyword="keyword"
        ></positionhit>
        <siftdoc :keyword="keyword"
                 @getSortway="getSortway"
                 @getAuthorId="getAuthorId"
                 @getKeyword="getKeyword"
        ></siftdoc>
        <documentList
                :keyword="keyword"
                :sortway="sortway"
                :authorId="authorId"
        ></documentList>
    </div>
</template>

<script>
    import positionhit from './Position'
    import documentList from '../../DocumentList'
    import siftdoc from '../../SiftDoc'

    export default {
        name: 'rightContentList',
        props: ['productId', 'productName'],
        components: {
            "positionhit": positionhit,
            "documentList": documentList,
            "siftdoc": siftdoc
        },
        data() {
            return {
                keyword: '',
                sortway: null,
                authorId: null
            }
        },
        methods: {
            //获取timeId排序方式
            getSortway(id) {
                this.sortway = id;
            },
            getAuthorId(id) {
                this.authorId = id;
            },
            getKeyword(keyword) {
                this.keyword = keyword;
            }
        }
    }
</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

    @media screen and (max-width: 768px) {
        .documentlist {
            width: calc(924px - 1184px + 100vw + 180px) !important;
        }
    }
</style>

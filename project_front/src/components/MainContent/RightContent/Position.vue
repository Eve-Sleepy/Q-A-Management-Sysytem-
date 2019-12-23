<template>
    <div class="positionhit">
        <img src="../../../../build/img/icon_home@2x.png" class="icon_home">
        <router-link to="/index">首页</router-link>
        <img src="../../../../build/img/icon_arrow@2x.png" v-show="productName" class="icon_arrow">
        <a @click="getProductId">{{productName}}</a>
    </div>
</template>

<script>
    export default {
        name: 'positionhit',
        props: ['productId', 'productName'],
        data: function () {
            return {}
        },
        methods: {
            getProductId() {
                this.$router.push({path: "/content", query: {productId: this.productId}});
            }
        }

    }
</script>

<style>
    .positionhit a {
        color: #666666;
        font-size: 12px;
        cursor: pointer;
    }

    .icon_home, .icon_arrow {
        height: 14px;
        vertical-align: middle;
        margin-left: 6px;
        margin-right: 6px;
    }

</style>

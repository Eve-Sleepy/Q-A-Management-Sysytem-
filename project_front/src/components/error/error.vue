<template>
    <div class="error">
        <div class="img">
            <img src="../../../build/img/error_page.png">
        </div>
        <el-button class='jumpBtn' @click="toLogin" type="primary" plain>马上登录！</el-button>
    </div>
</template>

<script>
    export default {
        name: "error",
        methods: {
            toLogin() {
                this.$router.push({path: '/login'})
            }
        }
    }

</script>

<style scoped>
    .jumpBtn {
        position: absolute;
        left: 430px;
        top: 540px;
    }

    .img {
        width: 100%;
        height: 100%;
    }
</style>

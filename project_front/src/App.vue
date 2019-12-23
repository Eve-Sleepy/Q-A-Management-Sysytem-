<template>
    <div id="app">
        <router-view v-if="isRouterAlive"></router-view>
    </div>
</template>

<script>
    export default {
        name: 'App',
        components: {},

        // 刷新页面
        provide() {
            return {
                reload: this.reload
            }
        },
        data() {
            return {
                isRouterAlive: true
            }
        },
        methods: {
            reload() {
                this.isRouterAlive = false;
                this.$nextTick(function () {
                    this.isRouterAlive = true;
                })
            }
        }
    }
</script>

<style>
    * {
        margin: 0;
        padding: 0;
        font-size: 14px;
        letter-spacing: 0.35px;
        font-family: -apple-system, BlinkMacSystemFont, Segoe UI,
        Roboto, PingFang SC, Hiragino Sans GB, Microsoft YaHei,
        Helvetica Neue, Helvetica, Arial, sans-serif
    }

    a, a:hover, a:focus {
        text-decoration: none;
    }

    .icon_top {
        vertical-align: bottom;
    }

    .icon_6_l {
        margin-left: 6px;
    }

    ul, ol {
        margin: 0;
    }

    ul, li {
        list-style: none;
    }
</style>

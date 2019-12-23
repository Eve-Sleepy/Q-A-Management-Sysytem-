<template>
    <div class="search">
        <div>
            <input type="text" class="input_search" placeholder="请输入关键字搜索" v-model="keyword" v-on:keyup.enter="search">
            <div type="submit" class="btn " @click="search">
                <img class="icon_search_m" src="../../../build/img/icon_search_m@2x.png">
            </div>
        </div>
        <!-- 标签    -->
        <ul class="tags_search">
            <li v-for="(tag,index) in tags.arr" :key="index" @click=" switchTag(tag.id, index)"
                :style="{'background-color':(!tagStaus[index]?`#3b4249`:`rgba(153,153,153,0.6)`)}">
                {{tag.name}}
            </li>
        </ul>
    </div>
</template>

<script>
    export default {
        name: 'search',
        data: function () {
            return {
                keyword: "",
                tags: {
                    total: 10,
                    arr: []
                },
                tagStaus: []
            }
        },
        methods: {
            search: function () {
                //向父组件传递搜索关键字
                this.$emit('getKeyword', this.keyword);
            },
            switchTag: function (tag_id, index) {
                // 根据index找到对应标签状态，为真则加入查询文档数组中
                if (this.tagStaus[index]) {

                    const tag = tag_id
                    this.$emit('getTagGroup', tag)
                    this.tagStaus.splice(index, 1, false)
                } else {

                    this.$emit('removeTagGroup', tag_id)
                    this.tagStaus.splice(index, 1, true)
                }

            }
        },
        created() {

            //标签列表
            this.$axios({
                method: 'get',
                url: "/api/tag/list",
                headers: {'Authorization': window.localStorage['Authorization']}
            }).then((res) => {
                if (res.status === 200) {
                    this.tags.arr = res.data.data.arr;
                    this.tags.arr.forEach(() => this.tagStaus.push(true))
                }
            })

        }

    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
    .search {
        width: 1184px;
        background-color: #003459;
        margin: 0 auto;
        overflow: hidden;
        padding-bottom: 28px;
    }

    .search > div {
        position: relative;
        padding-top: 40px;
        width: 924px;
        margin-left: 11%;
    }

    /* 搜索框 */
    .input_search {
        height: 48px;
        border-radius: 24px;
        border: none;
        padding: 14px 28px;
        width: 100%;
        outline: none;
        box-shadow: none;
    }

    .input_search::placeholder {
        color: #B3B3B3;
    }

    .search .btn {
        padding: 14px 28px;
        background: none;
        border: none;
        position: absolute;
        right: 0;
        top: 40px;
    }

    .icon_search_m {
        width: 16px;
        height: 16px;
        line-height: 100%;
    }

    /* ------标签-------- */
    .tags_search {
        margin-left: 11%;
        width: 940px;
        margin-top: 4px;
    }

    .icon_search_s {
        width: 16px;
    }

    .tags_search > li {
        background-color: rgba(153, 153, 153, 0.6);
        display: inline-block;
        padding: 3px 16px;
        color: white;
        border-radius: 20px;
        cursor: pointer;
        /* tag之间的间隔 */
        margin-top: 12px;
        margin-right: 16px;
    }


    @media screen and (max-width: 1184px) {
        .search {
            width: 100%;
        }

        .search > div, .tags_search {
            margin-left: calc(130px - 592px + 50vw);
        }
    }

    @media screen and (max-width: 944px) {
        .search > div {
            width: calc(924px - 944px + 100vw);
            margin-left: 10px;
        }

        .tags_search {
            width: calc(924px - 944px + 100vw + 16px);
            margin-left: 10px;
        }
    }
</style>


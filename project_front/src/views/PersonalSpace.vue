<template>
    <div>
        <headerview :isBtnShow="isBtnShow" ></headerview>
        <h2 style="text-align: center">您收到的私信</h2>
        <msglist/>
    </div>
</template>

<script>
    import headerview from '../components/Header'
    import msglist from '../components/MsgList'
export default {
    name: 'personalspace',
    components:{
        headerview,
        msglist
        },
    data(){
        return {
            isBtnShow: true
        }
    },
    methods:{

    }
}
</script>

<style scoped>

</style>

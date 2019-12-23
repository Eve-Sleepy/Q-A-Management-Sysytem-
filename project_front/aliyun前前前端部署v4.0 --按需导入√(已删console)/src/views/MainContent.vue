<template>
  <div class="maincontent">
    <headerview :isBtnShow="isBtnShow" ></headerview>
    <leftnav class="leftnav" ></leftnav>
    <div class="rightContent">
        <router-view :productId="productId" :productName="productName"></router-view>
    </div>
  </div>
</template>

<script>
import headerview from '../components/Header'
import leftnav from '../components/MainContent/LeftNav/LeftNav'

export default {
  name: 'maincontent',
  components:{
    "headerview":headerview,
    "leftnav":leftnav
  },
  data(){
    return{
      //默认显示第一个产品的内容？还是加一个全部产品选项？
      productId:null,
      productName:"全部产品",
      isBtnShow:true
    }
  },
  methods:{
    //接受来自侧边栏的传值
    showProduct:function(productId,productName){
        this.productId  = productId;
        this.productName  = productName;
    }
  }
}
</script>

<style scoped>
.leftnav{
  margin-top: 24px;
  float: left;
}
.rightContent{

  margin-top: 32px;
  margin-left: 40px;
  float: left;
}

@media screen and (min-width:1184px) {
  .leftnav{
    margin-left: calc(50vw - 592px);
  }
  .rightContent{
    margin-right: cal(50vw - 592px);
  }
}
@media screen and (max-width: 768px){
  .rightContent{
    margin-left: 40px;
  }
}
</style>

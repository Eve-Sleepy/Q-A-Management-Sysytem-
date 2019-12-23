<template>  
  <div id="leftnav" >
    <div class="leftnav1">
        <img class="icon_to_right"  @click="leftnavtoright" src="../../../../build/img/icon_toright@2x.png">
        <img class="icon_to_left" @click="leftnavtoleft" src="../../../../build/img/icon_toleft@2x.png">
        <productlist class="productlist"></productlist>
    </div>
    <div class="leftnav2"> 
        <span class="leftnav2span">产品</span>
        <img class="icon_to_right" @click="leftnavtoright"
            src="../../../../build/img/icon_toright@2x.png">
        <img class="icon_to_left" @click="leftnavtoleft" src="../../../../build/img/icon_toleft@2x.png">
        <productlist class="productlist"></productlist>
    </div>
  </div>
</template>

<script>
import productlist from './ProductList'
export default {
    name: 'leftNav',
    components:{
          productlist
    },
  methods:{
    showDetail:function(text){
    },
    leftnavtoleft:function(){       
        leftnavtoleft();  
    },
    leftnavtoright:function(){
        leftnavtoright();
    }
  },
  created(){
  }

}

//侧边栏收缩
function leftnavtoleft(){  
    //小视口情况
    if(document.documentElement.clientWidth<768){
        //收缩
        $(".leftnav2>.productlist").slideUp();
        //向左箭头不可见
        $(".leftnav2>.icon_to_left").css("display","none");
        //向右箭头可见
        $(".leftnav2>.icon_to_right").css("display","inline-block");
    }
    else{
        $(".leftnav1").css("width","48px");
        $(".leftnav1>.icon_to_left").css("display","none");
        $(".leftnav1>.icon_to_right").css("display","inline-block");
        $(".leftnav1>.productlist").css("display","none");
    }
}
//侧边栏扩展
function leftnavtoright(){
        if(document.documentElement.clientWidth<768){
            //下拉
            $(".leftnav2>.productlist").slideDown();
            $(".leftnav2>.icon_to_left").css("display","inline-block");
            $(".leftnav2>.icon_to_right").css("display","none"); 
        }
        else{
            $(".leftnav1").css("width","200px");
            $(".leftnav1>.icon_to_left").css("display","inline-block");
            $(".leftnav1>.icon_to_right").css("display","none"); 
            $(".leftnav1>.productlist").css("display","block");
        }   
}
</script>

<style scoped>

.leftnav1,.leftnav2>.productlist{
    background-color: rgba(255,255,255,1);
    padding-bottom: 12px;
    border-radius:6px;
    position: relative;
}
/* 展开的产品列表 */
.leftnav2>.productlist{
        position: fixed;
        padding: 12px 40px;
        margin-left:40px;
        width: 100%;
        background: #eff1f3;
        box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.12);
        right: 0;
        top:0;
        z-index: 1000;
        margin-top:60px;  
        border-radius: 0px;
}
    
/* 箭头 */
.icon_to_right,.icon_to_left{
    width: 24px;
    position:absolute;
    top:12px;
    right: 12px;
    cursor: pointer;
}

a {
    text-decoration: none;
}
.router-link-active {
    text-decoration: none;
}


@media screen and (min-width: 768px){
    .leftnav2{
    display: none;
    }
    .leftnav1{
    display: inline-block;
    width: 200px;
    padding-top:36px;
    }
    /* 收缩 */
    .leftnav1>.icon_to_left{ 
        display: inline-block;
    }
    /* 展开 */
    .leftnav1>.icon_to_right{
        display: none;
    }
    
}

/* ------------小视口 -----------------*/
@media screen and (max-width: 768px){
    .leftnav1{
        display: none;
    }
    .leftnav2
    {
        display: block;
    }

    .leftnav2>.productlist{
        display: none;
        
    }
    /* 收缩 */
    .leftnav2> .icon_to_left{ 
        display: none;
    }
    /* 展开 */
    .leftnav2>.icon_to_right{
        display: inline-block;
    }
    
    .leftnav2>.icon_to_left,.leftnav2>.icon_to_right{
        /* Rotate div */
        padding-bottom: 36px;
        height:60px;
        width: 24px;
        top:0px;
        right:28px;
        transform:rotate(90deg);
        -ms-transform:rotate(90deg);
        -moz-transform:rotate(90deg);
        -webkit-transform:rotate(90deg);
        -o-transform:rotate(90deg);
        position: fixed;
        z-index: 1002;
    }

    .leftnav2span{
        position: fixed;
        top:20px;
        right: 40px;
        color: #666;
        z-index: 1002;
    }
    

}
</style>

<template>
    <div class="login">
      <div id="loginarea">
        <img class="helelogo_l" src="../../build/img/helelogo_l@2x.png">
        <p>FAQ常见问题</p>
        <el-form :model="user" ref="user" :rules="rules">
          <el-form-item prop="username">
            <!-- 用户名输入框 -->
            <el-input
              v-model="user.username"  placeholder="请输入用户名"  prefix-icon="el-icon-user"  tabindex="1"
              autocomplete="on">
            </el-input>
          </el-form-item>
          <el-form-item prop="password">
            <!-- 密码输入框 -->
            <el-input
              v-model="user.password"  placeholder="请输入密码"  prefix-icon="el-icon-lock"
              show-password tabindex="2"
              autocomplete="on" @keyup.native="checkCapslock" @keyup.enter="submitForm">
            </el-input>
          </el-form-item>
          <el-form-item>
            <!-- 登录按钮  -->
            <el-button
              @click="submitForm('user')" type="success"
              tabindex="3">登录
            </el-button>
          </el-form-item>
        </el-form>

      </div>
    </div>
  </template>

  <script>
  export default {
    name: 'login',
    data(){
      return{
        user:{
          username:'',
          password:''
        },
        errors:{},
        rules:{
          username:[
            {required:true,message:"用户名不能为空",trigger:"blur"}
          ],
          password:[
            {required:true,message:"密码不能为空",trigger:"blur"}
          ]
        }
      }
    },
    methods:{
      //检测大小写是否锁定
      checkCapslock({ shiftKey, key } = {}) {
        if (key && key.length === 1) {
          if (shiftKey && (key >= 'a' && key <= 'z') || !shiftKey && (key >= 'A' && key <= 'Z')) {
            this.capsTooltip = true
          } else {
            this.capsTooltip = false
          }
        }
        if (key === 'CapsLock' && this.capsTooltip === true) {
          this.capsTooltip = false
        }
      },
      submitForm(user){
        this.$refs[user].validate((valid)=>{
          if(valid){
            //拿到用户信息，传递给后台
            this.$axios.post('/api/user/auth',this.user)
              .then(res=> {
                if(res.data.status === 200){
                  window.localStorage.setItem("Authorization",res.data.data.Authorization)
                  this.$router.push({path:"/index", params: { id:res.data.data.realname}});
                }
                else{
                  this.$message.error("用户名或密码错误");
                }
              })
              .catch(err =>{
              });
          }
          else{

          }
        });

      }
    }
  }
  </script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  #loginarea{
    margin: 10vh auto;
    width:390px;
    height:470px;
    background:white;
    box-shadow:0px 2px 10px 0px rgba(0,0,0,0.06);
    border-radius:10px;
  }
  #loginarea *{
    margin-left: 50%;
    transform:translateX(-50%);
  }
  /* 图标 */
  .helelogo_l{
    width: 60px;
    margin-top:56px;
    margin-bottom: 24px;
    text-align:center;
  }
  #loginarea .el-input{
    width:240px;
  }
  #loginarea p{
    font-size:16px;
    font-weight:600;
    color:rgba(0,52,89,1);
    margin-bottom: 56px;
    text-align: center;
    margin-left: 0;
    transform: none;
  }
  #loginarea .el-button{
    display: block;
    width: 240px;
    margin-top: 12px;
  }

  /* 登录按钮 */
  #loginarea .el-button--success {
    color: #FFF;
    background-color: #41B298;
    border-color: #41B298;
}
  #loginarea .el-button--success:active{
    background-color:rgb(104, 207, 183) ;
  }
</style>

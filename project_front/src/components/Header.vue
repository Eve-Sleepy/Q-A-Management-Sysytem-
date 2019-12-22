<template>
  <div>
    <nav class="headerview">
      <div class="header_container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <img class="logo" src="../../build/img/helelogo_s@2x.png">
          <router-link class="brand" to='/index'>FAQ常见问题</router-link>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div  id="bs-example-navbar-collapse-1" aria-expanded="false" style="height: 1px;">
          <ul class="nav navbar-nav">
            <!-- 新建按钮 -->
            <li>
              <el-popover
                      placement="top-start"
                      width="auto"
                      trigger="hover">
                <el-button class="btn_new_sth" type="text" @click="centerDialogVisible = true">新建产品</el-button>
                <router-link to="/content/create"><el-button class="btn_new_sth" type="text" >
                  新建文档</el-button>
                </router-link>
                <el-button slot="reference" class="button_add">
                  <img class="icon_add" src="../../build/img/icon_add_doc@2x.png">
                </el-button>
              </el-popover><!--  -->
            </li>
            <!-- 用户 -->
            <li class="dropdown" :title="username">
              <a href="#" class="username dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                <el-badge is-dot :hidden="notCheckedMsg === 0"><img class="icon_user" src="../../build/img/icon_user@2x.png"></el-badge>

              </a>
              <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                <li>
                  <router-link to="/personalSpace" >
                    <el-badge :value="notCheckedMsg"  :hidden="notCheckedMsg === 0" class="item">
                      <span>个人中心</span>
                    </el-badge>
                  </router-link>
                </li>

                <li ><a class="person" href="#" @click.prevent="changePwdDialogVisible = true">修改密码</a></li>

                <li ><a class="person" href="#" @click.prevent="logout">退出</a></li>
              </ul>
            </li>
            <li class="leftNav_btn" v-show="isBtnShow"></li>
          </ul>
        </div>
      </div>
      <!-- 新建产品弹窗 -->
      <el-dialog
              title="新建产品"
              :visible.sync="centerDialogVisible"
              :modal-append-to-body='false'
              width="30%"
              center>
        <el-input type="textarea" autosize placeholder="输入产品名称"
                  v-model="product.name" maxlength="20" show-word-limit>
        </el-input>
        <div class="color_input">
          <span>主题色：</span>
          <el-color-picker size="medium" v-model="product.color"  :predefine="predefineColors"> </el-color-picker>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button @click="centerDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="newProduct">确 定</el-button>
          </span>
      </el-dialog>

      <!--   修改密码弹窗     -->
      <el-dialog
              title="修改密码"
              :visible.sync="changePwdDialogVisible"
              :modal-append-to-body='false'
              width="30%"
              center>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="70px" class="demo-ruleForm">

          <el-form-item label="旧密码" prop="oldpass">
            <el-input type="password" v-model="ruleForm.oldpass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="修改密码" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </nav>
    <div style="height: 60px;"></div>
  </div>
</template>

<script>
  import jwtDecode from 'jwt-decode'
  export default {
    name: 'headerview',
    props:['isBtnShow'],
    inject:['reload'],
    data(){
      // 验证密码合法性的函数
      let validateOldPass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.pass !== '') {
            this.$refs.ruleForm.validateField('pass');
          }
          callback();
        }
      };
      let validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (value === this.ruleForm.oldpass) {
            callback(new Error('新旧密码不一能一样!'));
          }else if(this.ruleForm.checkPass !== ''){
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      let validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };

      return{
        decode: jwtDecode(window.localStorage.Authorization),
        userId: null,
        editPwd:{},
        centerDialogVisible: false,
        changePwdDialogVisible: false,
        username:null,
        product:{
          name:"",
          color:"#41B298",
          imageUrl:null,
          describe: null
        },
        notCheckedMsg: 0,
        //预定义的颜色
        predefineColors:["#ff6688","#41B298",
          "#409EFF","#67C23A","#909399","#E08E2A"],
        ruleForm: {
          oldpass: '',
          pass: '',
          checkPass: ''
        },
        rules: {
          oldpass:[
            { validator: validateOldPass, trigger: 'blur' }
          ],
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ]
        }
      }

    },
    created(){
      this.username = this.decode.realname
      this.userId = Number(this.decode.userId)
    },
    mounted(){
      let search = {
        'userId': this.userId,
        'currentPage': null,
        'perPage': null
      };
      this.$axios({
        method: 'post',
        url: "/api/msg/list",
        headers: {'Authorization':window.localStorage['Authorization']},
        data: search
      }).then(res=>{
        if(res.data.status === 200){
          this.notCheckedMsg =  res.data.data.arr.filter(msg=> (msg.state ===0) ).length;
        }else{
          this.notCheckedMsg = 0;
        }
      }).catch( error =>{
        console.log(error);
      });
    },
    methods:{
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$confirm('确认修改密码, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              //点击确定按钮的操作, 提交修改的操作
              this.editPwd.userId = Number(this.userId);
              this.editPwd.editPassword = this.ruleForm.pass;
              this.editPwd.oldPassword = this.ruleForm.oldpass;
              this.$axios({
                method: 'post',
                url: "/api/user/editPwd",
                headers: {'Authorization':window.localStorage['Authorization']},
                data: this.editPwd
              }).then(res=> {
                console.log(res.data);
                if(res.data.status === 200){
                  //刷新页面
                  this.$message.success("修改密码成功");
                  this.reload();
                }else if(res.data.status === 501){
                  this.$message.error("修改失败, "+res.data.msg);
                  this.reload();
                  return false;
                }
              }) .catch( error =>{
                console.log(error);
              });
            })
          } else {
            this.$message.error("修改失败");
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      dialogAddPro:function(){
      },

      logout(){
        this.$router.push({path: '/'})
        window.localStorage.clear()
      },
      newProduct(){
        this.centerDialogVisible = false;

        this.$axios({
          method: 'post',
          url: "/api/product/create",
          headers: {'Authorization':window.localStorage['Authorization']},
          data: this.product
        }).then(res=> {
          console.log(res.data);
          if(res.status === 200){
            //提示
            this.$message({
              message: '新建产品成功',
              type: 'success'
            });
            //刷新页面
            this.reload();
          }
        }).catch( error =>{
                  console.log(error);
        });
      }


    }
  }
</script>
<style scoped>
  .person{
    height: 40px;
    margin-top: 10px;
  }

  .item {
    margin-bottom: 10px;
    margin-right: 50px;
  }
  .el-badge__content{
    margin: 0 0 30px 40px;
  }
  .headerview{
    width: 100%;
    height: 60px;
    margin: 0 auto;
    background-color: white;
    box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.06);
    position: fixed;
    z-index: 1001;
  }
  .header_container{
    max-width: 1160px;
    padding-left: 12px;
    padding-right: 12px;
    margin: auto;
    height: 60px;
    position: relative;
  }
  .logo{
    width: 20px;
    height: 24px;
    vertical-align: middle;
  }
  .brand{
    margin-left: 8px;
    color: #003459;
    position: relative;
    line-height: 60px;
    text-decoration: none;
  }
  a {
    text-decoration: none;
  }
  .router-link-active {
    text-decoration: none;
  }
  .headerview_right{
    float: right;
  }

  .nav > li > a{
    padding:16px 12px;
  }
  .username{
    margin-left: 20px;
    color:#003459;
  }
  .icon_arrowlist{
    width: 13px;
    height: 8px;
  }
  .icon_user{
    width: 28px;
    vertical-align: middle;
  }
  /* 新建按钮 */
  .button_add{
    width: 40px;
    height: 28px;
    background-color:#41B298 ;
    border-radius:6px;
    border: none;
    margin-top: 16px;
  }
  .button_add:hover,.button_add:focus{
    background-color:rgb(104, 207, 183) ;
  }
  .color_input{
    padding-top:16px;
  }
  .color_input>.el-color-picker{
    vertical-align: bottom;
  }
  .icon_add{
    width:12px;
    position: relative;
    vertical-align: top;
    top:-3px;
    left: -5px;
  }
  .btn_new_sth{
    font-weight: normal;
    color: #666;
  }
  .dropdown-menu{
    border: none;
    box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.06);
    min-width: 100px;
  }
  /* ----搜索框---- */
  .headerview .navbar-form{
    margin-top: 13px;
    padding-left: 32px;
  }
  .headerview .navbar-form .form-group input{
    padding: 6px 10px;
    font-size: 12px;
    color: #666;
    background:#F5F5F5;
    border-radius:6px;
    border: none;
    box-shadow: none;
    height: 28px;
  }
  .headerview .navbar-form .btn{
    padding: 6px;
    background:none;
  }
  .form-control::placeholder{
    color:#d9d9d9;
  }
  /* 图标 */
  .icon_search_s{
    width: 16px;
  }

  .navbar-nav{
    float: right;
    margin: 0;
  }

  @media screen and (max-width:768px){
    .navbar-header {
      float: left;
    }

    .navbar-right {
      display: inline-block;
      float: right !important;
      margin-right: 2px;
    }
    .navbar-right .dropdown-menu{
      right: 0;
      left: auto;
    }
    .navbar-nav > li {
      float: left;
    }
    .navbar-collapse.collapse{
      display: block !important;
      height: auto !important;
      padding-bottom: 0;
      overflow: visible !important;
    }
    .navbar-collapse {
      width: auto;
      border-top: 0;
      -webkit-box-shadow: none;
      box-shadow: none;
    }
    .navbar-left {
      float: left !important;
    }
    .navbar-form {
      display: inline-block;
      width: auto;
      padding-top: 0;
      padding-bottom: 0;
      margin-right: 0;
      margin-left: 0;
      border: 0;
      -webkit-box-shadow: none;
      box-shadow: none;
      padding-left: 12px !important;
    }
    .navbar-form .form-group {
      display: inline-block;
      margin-bottom: 0;
      vertical-align: middle;
    }
    .username{
      margin-left: 14px;
    }
    .leftNav_btn{
      width: 60px;
    }
  }
  @media screen and (max-width: 767px) {
    .dropdown-menu {
      position: absolute !important;
      float: right !important;
      -webkit-box-shadow:0 2px 10px 0 rgba(0, 0, 0, 0.06) !important;
      box-shadow: 0 2px 10px 0 rgba(0, 0, 0, 0.06) !important;
      background-color: white !important;
    }
    .el-dialog{
      min-width: 260px;
    }
  }
  @media screen and (max-width: 550px){
    .form-group input{
      display: none;
    }
  }


</style>

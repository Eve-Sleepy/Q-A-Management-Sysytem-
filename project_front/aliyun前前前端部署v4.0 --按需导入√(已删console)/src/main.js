// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import juqery from 'jquery'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.js'

import axios from 'axios'
import apiConfig from '../config/api.config'

import {
  Pagination,
  Dialog,
  Dropdown,
  DropdownMenu,
  DropdownItem,
  Input,
  InputNumber,
  Select,
  Option,
  OptionGroup,
  Button,
  ButtonGroup,
  Form,
  FormItem,
  Tag,
  Icon,
  Badge,
  ColorPicker,
  Transfer,
  Popover,
  Divider,
  MessageBox,
  Message,
  Loading
} from 'element-ui';


Vue.use(Pagination);
Vue.use(Dialog);
Vue.use(Dropdown);
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
Vue.use(Input);
Vue.use(InputNumber);
Vue.use(Select);
Vue.use(Option);
Vue.use(OptionGroup);
Vue.use(Button);
Vue.use(ButtonGroup);
Vue.use(Popover);
Vue.use(Form);
Vue.use(FormItem);
Vue.use(Tag);
Vue.use(Icon);
Vue.use(Badge);
Vue.use(Divider);
Vue.use(ColorPicker);
Vue.use(Transfer);


Vue.use(Loading.directive);

Vue.prototype.$loading = Loading.service;
Vue.prototype.$prompt = MessageBox.prompt;
Vue.prototype.$confirm=MessageBox.confirm;
Vue.prototype.$alert=MessageBox.alert;
Vue.prototype.$message = Message;


Vue.prototype.$axios= axios
Vue.config.productionTip = false
axios.defaults.baseURL = apiConfig.baseUrl;
axios.defaults.withCredentials = false;


/* eslint-disable no-new */
//配置对象的属性名都是一些确定的名称，不能随便修改
new Vue({   
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})






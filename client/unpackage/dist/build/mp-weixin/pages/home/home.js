(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["pages/home/home"],{"1f10":function(e,t,n){"use strict";(function(e){Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var o=u(n("1e25"));function u(e){return e&&e.__esModule?e:{default:e}}var s={data:function(){return{pic:"https://uviewui.com/common/logo.png",show:!1,nick:"未登录",userNo:"未绑定",deptName:"未绑定",userInfo:"",admin:"",showModel:"",c:[],selObj:{},student:{name:"",userNo:"",deptFk:"",project:"",phone:"",deptNo:""},deptList:[]}},onShow:function(){this.checkUser()},methods:{showAppInfo:function(){e.showModal({title:"关于",content:"校园信息应用，版本号0.012",showCancel:!1})},checkUser:function(){this.userInfo=o.default.getUserInfo(),this.userInfo&&(this.admin=0==this.userInfo.userInfo.type,this.nick=this.userInfo.userInfo.nick,this.userNo=this.userInfo.userInfo.userNo)},selConfirm:function(e){this.selObj=e[0];for(var t=0;t<this.deptList.length;t++)if(this.deptList[t].value==this.selObj.value){this.selObj["no"]=this.deptList[t].no;break}},getDeptInfo:function(e){var t=this;o.default.request({method:"GET",url:o.default.getDeptData,success:function(n){t.deptList=[],n.data.data.map((function(n,o,u){e==n.level&&t.deptList.push({value:n.id,label:n.name,no:n.deptNo})}))},fail:function(e){}})},submitCheck:function(){var t=this;if(t.student.deptFk=t.selObj.value,t.student.deptNo=t.selObj.no,console.log(JSON.stringify(t.student)),t.student.name&&t.student.userNo&&t.student.deptFk&&t.student.project){try{var n=Number(((new Date).getFullYear()+"").slice(2,4)),u=Number((t.student.userNo+"").slice(0,2)),s=(t.student.userNo+"").slice(2,4);if(n-u>4||n-u<0||s!=t.selObj.no||10!=(t.student.userNo+"").length)return void e.showToast({title:"学号错误",icon:"error"})}catch(i){return void e.showToast({title:"验证错误",icon:"error"})}wx.login({success:function(n){if(n.code){var u=n.code;o.default.request({method:"GET",data:{code:u,data:JSON.stringify(t.student)},url:o.default.checkStudent,success:function(n){200==n.data.code?(t.showModel=!1,t.getUserInfo(u),e.showToast({icon:"none",title:"认证成功"})):e.showToast({title:n.data.msg,icon:"none"})},fail:function(e){}})}else console.log("登录失败！"+n.errMsg)}})}else e.showToast({title:"请填写完整",icon:"none"})},checkStudent:function(){var e=this;e.showModel=!0,e.getDeptInfo(1)},showInfo:function(){var t=this;this.userInfo.token?e.showToast({title:"已完成授权,无需重复认证",icon:"none"}):wx.login({success:function(n){if(n.code){var o=n.code;e.showModal({title:"登录",content:"请授权获取微信信息用于登录",success:function(e){e.confirm&&t.getUserInfo(o)}})}else console.log("登录失败！"+n.errMsg)}})},getUserInfo:function(t){console.log(t);var n=this;wx.getUserProfile({desc:"用于学生身份认证",success:function(u){console.log(3),console.log(JSON.stringify(u)),n.nick=u.userInfo.nickName,n.pic=u.userInfo.avatarUrl,o.default.request({method:"GET",data:{code:t,enData:u.encryptedData,iv:u.iv,userInfo:u.userInfo},url:o.default.loginCheck,success:function(t){if(200==t.data.code)try{e.setStorageSync("userInfo",t.data.data),n.checkUser()}catch(o){e.clearStorage()}},fail:function(e){}})}})}}};t.default=s}).call(this,n("543d")["default"])},"213b":function(e,t,n){"use strict";n.r(t);var o=n("1f10"),u=n.n(o);for(var s in o)"default"!==s&&function(e){n.d(t,e,(function(){return o[e]}))}(s);t["default"]=u.a},"24b1":function(e,t,n){"use strict";var o=n("44d3"),u=n.n(o);u.a},"3a0e":function(e,t,n){"use strict";n.r(t);var o=n("75ba"),u=n("213b");for(var s in u)"default"!==s&&function(e){n.d(t,e,(function(){return u[e]}))}(s);n("24b1");var i,c=n("f0c5"),r=Object(c["a"])(u["default"],o["b"],o["c"],!1,null,null,null,!1,o["a"],i);t["default"]=r.exports},"44d3":function(e,t,n){},"4f52":function(e,t,n){"use strict";(function(e){n("18fa");o(n("66fd"));var t=o(n("3a0e"));function o(e){return e&&e.__esModule?e:{default:e}}wx.__webpack_require_UNI_MP_PLUGIN__=n,e(t.default)}).call(this,n("543d")["createPage"])},"75ba":function(e,t,n){"use strict";n.d(t,"b",(function(){return u})),n.d(t,"c",(function(){return s})),n.d(t,"a",(function(){return o}));var o={uNavbar:function(){return n.e("uview-ui/components/u-navbar/u-navbar").then(n.bind(null,"500b"))},uAvatar:function(){return n.e("uview-ui/components/u-avatar/u-avatar").then(n.bind(null,"292f"))},uCellGroup:function(){return n.e("uview-ui/components/u-cell-group/u-cell-group").then(n.bind(null,"e0dc"))},uCellItem:function(){return n.e("uview-ui/components/u-cell-item/u-cell-item").then(n.bind(null,"f6e2"))},uPopup:function(){return n.e("uview-ui/components/u-popup/u-popup").then(n.bind(null,"d3d1"))},uField:function(){return n.e("uview-ui/components/u-field/u-field").then(n.bind(null,"c184"))},uButton:function(){return n.e("uview-ui/components/u-button/u-button").then(n.bind(null,"23c9"))},uSelect:function(){return n.e("uview-ui/components/u-select/u-select").then(n.bind(null,"32b4"))}},u=function(){var e=this,t=e.$createElement;e._self._c;e._isMounted||(e.e0=function(t){e.show=!0})},s=[]}},[["4f52","common/runtime","common/vendor"]]]);
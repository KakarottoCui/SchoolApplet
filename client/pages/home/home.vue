<template>
	<view>
		<u-navbar :is-back="false" title="　" :border-bottom="false">
			<view class="u-flex u-row-right" style="width: 100%;">
				<view class="camera u-flex u-row-center">

				</view>
			</view>
		</u-navbar>
		<view class="u-flex user-box u-p-l-30 u-p-r-20 u-p-b-30">
			<view class="u-m-r-10">
				<u-avatar :src="pic" size="140"></u-avatar>
			</view>
			<view class="u-flex-1">
				<view class="u-font-18 u-p-b-20">{{nick}}</view>
				<view class="u-font-14 u-tips-color">学号:{{userNo}}</view>
				<view class="u-font-14 u-tips-color">身份:{{admin ? '管理员':(userInfo.token ? '学生':'游客，请完成学生认证明')}}</view>
			</view>
			<!-- <view class="u-m-l-10 u-p-10">
				<u-icon name="scan" color="#969799" size="28"></u-icon>
			</view> -->
			<!-- <view class="u-m-l-10 u-p-10">
				<u-icon name="arrow-right" color="#969799" size="28"></u-icon>
			</view> -->
		</view>

		<view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item :iconStyle="{color:'#3f80de'}" @click="showInfo" icon="lock-fill" title="授权登录">
				</u-cell-item>
				<u-cell-item :iconStyle="{color:'#3f80de'}" @click="checkStudent" icon="man-add-fill" title="学生认证"></u-cell-item>
				<u-cell-item :iconStyle="{color:'#3f80de'}" @click="showAppInfo" icon="question-circle-fill" title="关于小程序"></u-cell-item>
			</u-cell-group>
		</view>

		<!-- <view class="u-m-t-20">
			<u-cell-group>
				<u-cell-item :iconStyle="{color:'#3f80de'}" icon="setting" title="设置"></u-cell-item>
			</u-cell-group>
		</view> -->

		<u-popup v-model="showModel" mode="center" border-radius="14" width="600">
			<view class="c-model-view u-p-25">

				<u-field v-model="student.name" label="姓名" placeholder="请填写姓名"></u-field>
				<u-field v-model="student.userNo" label="学号" type="number" placeholder="请填写学号"></u-field>
				<u-field v-model="selObj.label" @click="show = true" :disabled="true"  label="院系" placeholder="请选择院系"></u-field>
				<u-field v-model="student.project" label="专业" placeholder="请填写专业"></u-field>
				<u-field v-model="student.phone" type="number" label="手机号" placeholder="请选择手机号"></u-field>
				
				<text class="u-p-10 u-tips-color">认证后请返回首屏重新授权</text>
				
				<u-button type="primary" class="u-m-t-50" @click="submitCheck"  :ripple="true">认证
					  </u-button>
			</view>
		</u-popup>
		
		<u-select @confirm="selConfirm" v-model="show" :list="deptList"></u-select>
		
	</view>
</template>

<script>
	import appRequest from "@/common/appRequestUrl.js"
	export default {
		data() {
			return {
				pic: 'https://uviewui.com/common/logo.png',
				show: false,
				nick: "未登录",
				userNo: "未绑定",
				deptName: "未绑定",
				userInfo: "",
				admin: "",
				showModel: "",
				c:[],
				selObj:{},
				student:{
					name:"",
					userNo:"",
					deptFk:"",
					project:"",
					phone:"",
					deptNo:""
				},deptList:[]
			}
		},
		onShow() {
			this.checkUser();

		},
		methods: {
			showAppInfo:function(){
				uni.showModal({
					title:"关于",
					content:"校园信息应用，版本号0.012",
					showCancel:false
				})
			},
			checkUser:function(){
				this.userInfo = appRequest.getUserInfo();
				if (this.userInfo) {
					this.admin = this.userInfo.userInfo.type == 0;
					this.nick = this.userInfo.userInfo.nick;
					this.userNo = this.userInfo.userInfo.userNo;
				}
			},
			selConfirm(e) {
				this.selObj = e[0];
				for(let i=0;i<this.deptList.length;i++){
					if(this.deptList[i].value == this.selObj.value){
						this.selObj['no'] = this.deptList[i].no;
						break;
					}
				}
		
			},
			getDeptInfo: function(level) {
				let _this = this;
				appRequest.request({
					method: "GET",
					url: appRequest.getDeptData,
					success: function(res) {
						_this.deptList = [];
						res.data.data.map(function(item, index, arr) {
							if(level == item.level){
								_this.deptList.push({
									value: item.id,
									label: item.name,
									no:item.deptNo
								})
							}
						});
					},
					fail: function(res) {
			
					}
				})
			},
			submitCheck(){
				let _this = this;
				_this.student.deptFk = _this.selObj.value;
				_this.student.deptNo = _this.selObj.no;
				console.log(JSON.stringify(_this.student))
				if(!_this.student.name || !_this.student.userNo || !_this.student.deptFk || !_this.student.project ){
					uni.showToast({
						title:"请填写完整",
						icon:"none"
					})
					return;
				}
				
				try{
					let year = Number((new Date().getFullYear() + "").slice(2,4));
					let usrYear = Number((_this.student.userNo+"").slice(0,2));
					let dept = (_this.student.userNo+"").slice(2,4);
					if(year - usrYear > 4 ||  year - usrYear < 0 || dept != _this.selObj.no || (_this.student.userNo+"").length!=10){
						uni.showToast({
							title:"学号错误",
							icon:"error"
						})
						return;
					}
				}catch(e){
					//TODO handle the exception
					uni.showToast({
						title:"验证错误",
						icon:"error"
					})
					return;
				}
				
				wx.login({
					success(res) {
						if (res.code) {
							let code = res.code;
							
							appRequest.request({
								method: "GET",
								data: {
									code: code,
									data: JSON.stringify(_this.student)
								},
								url: appRequest.checkStudent,
								success: function(res) {
									if (res.data.code == 200) {
										_this.showModel = false;
										_this.getUserInfo(code);
										uni.showToast({
											icon:"none",
											title:"认证成功"
										})
									}else{
										uni.showToast({
											title:res.data.msg,
											icon:"none"
										})
									}
								},
								fail: function(res) {
								}
							})
							
						} else {
							console.log('登录失败！' + res.errMsg)
						}
					}
				})
				
			},
			checkStudent() {
				let _this = this;
				// if (this.userInfo.token) {
				// 	uni.showToast({
				// 		title: '已完成学生认证,无需重复认证',
				// 		icon: "none"
				// 	})
				// 	return;
				// }
				_this.showModel = true;
				_this.getDeptInfo(1);
			},
			showInfo: function() {
				let _this = this;
				if (this.userInfo.token) {
					uni.showToast({
						title: '已完成授权,无需重复认证',
						icon: "none"
					})
					return;
				}
				wx.login({
					success(res) {
						if (res.code) {
							let code = res.code;
							uni.showModal({
								title: "登录",
								content: "请授权获取微信信息用于登录",
								success: function(res) {
									if (res.confirm) {
										_this.getUserInfo(code);
									}
								}
							})


						} else {
							console.log('登录失败！' + res.errMsg)
						}
					}
				})
			},
			getUserInfo(code) {
				console.log(code);
				let _this = this;
				wx.getUserProfile({
					desc: '用于学生身份认证', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
					success: (res) => {
						console.log(3);
						console.log(JSON.stringify(res));
						_this.nick = res.userInfo.nickName;
						_this.pic = res.userInfo.avatarUrl;
						appRequest.request({
							method: "GET",
							data: {
								code: code,
								enData: res.encryptedData,
								iv: res.iv,
								userInfo: res.userInfo
							},
							url: appRequest.loginCheck,
							success: function(res) {
								if (res.data.code == 200) {
									try {
										uni.setStorageSync('userInfo', res.data.data);
										_this.checkUser();
									} catch (e) {
										uni.clearStorage();
									}
								}
							},
							fail: function(res) {
							}
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		min-height: 100vh;
		background-color: rgba(144, 215, 236, 0.05);

	}

	.camera {
		width: 54px;
		height: 44px;

		&:active {
			background-color: #ededed;
		}
	}

	.user-box {
		background-color: #fff;
	}
</style>

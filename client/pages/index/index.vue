<template>
	<view class="content">
		<view :class="topNavStyle.class" :style="topNavStyle.style" class="top-back"></view>
		<view class="swipe-view" style="text-align: center;padding: 23% 5% 5px 5%;">
			<u-swiper border-radius="18" bg-color="rgba(255,255,255,0)" :effect3d="true" mode="rect" :list="list1"
				@click="click()"></u-swiper>
			<!-- <u-swiper indicatorMode="dot" :indicator="true" :list="tipList"></u-swiper> -->
		</view>
		<view class="tip" style="padding: 2% 5% 0 5%; display: flex; justify-content: center;">
			<u-tabs bg-color="rgba(255,255,255,0)" :list="tipList" :is-scroll="true" :current="tipCurrent"
				@change="click"></u-tabs>
			<!-- <u-tabs active-color="#102b6a" inactive-color="white" :list="tipList" :current="tipCurrent" @change="change"></u-tabs> -->
			<!-- <u-tabs lineColor="#102b6a" :inactiveStyle="{color:'white'}" :activeStyle="{color:'#102b6a'}" :list="tipList" @click="click"></u-tabs> -->
		</view>
		<view class="sc-card">
			<u-card @click="showItem(item)" v-for="(item,index) in articleList" :key="index" padding="20"
				box-shadow="3px 3px 10px rgba(0,0,0,0.1)" :border="false" :title="item.title" :sub-title="item.deptName"
				:thumb="getIcon()">
				<view class="" slot="body">
					<view class="u-body-item u-flex u-col-between u-p-t-0">
						<view class="u-body-item-title u-line-2">{{item.context}}</view>
						<image @click.stop="showPic(item.imgList[0].base64)" :src="item.imgList[0].base64" mode="aspectFill">
						</image>
					</view>
				</view>
				<view class="u-flex u-row-between" slot="foot">
					<u-icon name="chat-fill" size="34" color="" label="评论"></u-icon>
					<text>{{item.createTime}}</text>
					<text v-if="admin" style="color: #DD524D;" @click="delArticle(item)">删除</text>
				</view>
			</u-card>
		</view>
		
		<view v-if="admin" class="add-btn blue-btn" @click="addArticle" >
			<u-icon name="plus" class="sc-text-shadow"></u-icon>
		</view>

	</view>
</template>

<script>
	import appRequest from "@/common/appRequestUrl.js"
	export default {
		data() {
			return {
				articleList: [],
				show: true,
				list1: [
					appRequest.baseUrl + "/img/sw1.png",
					appRequest.baseUrl + "/img/sw2.png",
					appRequest.baseUrl + "/img/sw3.png",
					appRequest.baseUrl + "/img/sw4.png"
				],
				tipList: [{
					name: '学校通知'
				}, {
					name: '各院动态'
				}],
				tipCurrent: 0,
				admin:false,
				userInfo:""
			}
		},
		computed: {

			topNavStyle() {
				let r = this.scrollTop / 100;
				console.log(this.scrollTop)
				let flag = r >= 1.68;
				return {
					"class": flag ? 'style2 bg-white text-white' : '',
					"style": `padding-top:${this.StatusBar}px;background-color: rgba(255,255,255,${flag?1:0});`
				}
			}
		},
		onLoad() {
			
		},onShow:function(){
			this.userInfo = appRequest.getUserInfo();
			if(!this.userInfo.token){
				this.showInfo()
			}else{
				this.admin = this.userInfo.userInfo.type == 0;
				this.getAtricle();
			}
		},
		methods: {
			delArticle:function(item){
				let _this = this;
				appRequest.request({
					method: "GET",
					data: {
						id: item.id
					},
					url: appRequest.delArticle,
					success: function(res) {
						_this.getAtricle();
					},
					fail: function(res) {
				
					}
				})
			},
			addArticle:function(){
				let _this = this;
				uni.navigateTo({
					url:"../common/addArticle?type="+_this.tipCurrent+"&level="+_this.tipCurrent +"&name="+_this.tipList[_this.tipCurrent]
				})
			},
			showItem: function(item) {
				try {
					uni.setStorageSync('article', item);
					uni.navigateTo({
						url: "../common/reviewArticle"
					})
				} catch (e) {
					// error
					uni.showToast({
						icon: "none",
						title: "数据异常"
					})
				}
			},
			showPic: function(pic) {
				uni.previewImage({
					current: 0,
					urls: [pic]
				});
			},
			getIcon: function() {
				return appRequest.baseUrl + '/img/school' + (this.tipCurrent == 0 ? '2' : '') + '.png'
			},
			getAtricle: function() {
				let _this = this;
				appRequest.request({
					method: "GET",
					data: {
						data: {
							type: _this.tipCurrent,
							showType: 0
						}
					},
					url: appRequest.getArticle,
					success: function(res) {

						//alert(JSON.stringify(res.data.data))
						_this.articleList = res.data.data;
					},
					fail: function(res) {

					}
				})
			},
			click: function(index) {
				this.tipCurrent = index;
				this.getAtricle();
			},
			showInfo: function() {
				let _this = this;
				wx.login({
					success(res) {
						if (res.code) {
							let code = res.code;
							uni.showModal({
								title:"登录",
								content:"请授权获取微信信息用于登录",
								success:function(res){
									if(res.confirm){
										_this.getUserInfo(code);
									}else{
										_this.getAtricle();
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
								if(res.data.code == 200){
									try {
										uni.setStorageSync('userInfo', res.data.data);
										_this.getAtricle();
									} catch (e) {
										uni.clearStorage();
									}	
								}else{
									_this.getAtricle();
								}
							},
							fail: function(res) {
								_this.getAtricle();
							}
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	// .sc-card{
	// 	display: flex;
	// 	margin: 20px 5% 20px 5%;
	// 	justify-content: center;
	// 	align-items: center;
	// }
	// .content {
	// 	display: flex;
	// 	flex-direction: column;
	// 	align-items: center;
	// 	justify-content: center;
	// }

	// .logo {
	// 	height: 200rpx;
	// 	width: 200rpx;
	// 	margin-top: 200rpx;
	// 	margin-left: auto;
	// 	margin-right: auto;
	// 	margin-bottom: 50rpx;
	// }

	// .text-area {
	// 	display: flex;
	// 	justify-content: center;
	// }

	// .title {
	// 	font-size: 36rpx;
	// 	color: #8f8f94;
	// }
	// .swipe-view{
	// 	display: flex;
	// 	justify-content: center;
	// }
	.top-back {
		position: fixed;
		top: 0;
		left: 0;
		height: 40vh;
		width: 100vw;
		//background: linear-gradient(to right, #6cacff 0%, #8debff 100%);
		background: linear-gradient(to bottom, rgba(108, 172, 255, 1), rgba(141, 235, 255, 1), rgba(108, 172, 255, 0), rgba(141, 235, 255, 0));
		z-index: 0;
	}

	.content {
		min-height: 100vh;
		background-color: rgba(144, 215, 236, 0.1);

	}

	.u-card-wrap {
		background-color: $u-bg-color;
		padding: 1px;
	}

	.u-body-item {
		font-size: 32rpx;
		color: #333;
		padding: 20rpx 10rpx;
	}

	.u-body-item image {
		width: 120rpx;
		flex: 0 0 120rpx;
		height: 120rpx;
		border-radius: 8rpx;
		margin-left: 12rpx;
	}
	
	.blue-btn{
		background-color: rgba(108,172,255,0.8);
		box-shadow: 1px 1px 6px rgba(108,172,255,1);
			
	}
	
	.add-btn{
		position: fixed;
		right: 30px;
		bottom: 30px;
		border-radius: 30px;
		width: 60px;
		height: 60px;
		color: white;
		font-size: 40px;
		line-height: 60px;
		text-align: center;
	}
</style>

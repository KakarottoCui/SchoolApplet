<template>
	<view class="content" :class="bgClass">
		<u-navbar :border-bottom="false" v-if="pageType > -1" :back-text-style="{color:'white'}" back-icon-color="white"
			back-text="返回" title-color="white" :titleBold="true" :title="pageName[pageType]" :background="background">
			<!-- <view slot="right" class="sc-text-shadow u-m-r-20" style="color: white;" >发一条</view> -->
		</u-navbar>
		<view v-if="pageType < 2" class="tip" style="padding: 2% 5% 0 5%; display: flex; justify-content: center;">
			<u-tabs :active-color="tabActColor" bg-color="rgba(255,255,255,0)" :list="pageTabList[pageType]" :is-scroll="true" :current="tipCurrent" @change="clickTab"></u-tabs>
			<!-- <u-tabs active-color="#102b6a" inactive-color="white" :list="tipList" :current="tipCurrent" @change="change"></u-tabs> -->
			<!-- <u-tabs lineColor="#102b6a" :inactiveStyle="{color:'white'}" :activeStyle="{color:'#102b6a'}" :list="tipList" @click="click"></u-tabs> -->
		</view>
		<view class="sc-card" >
			<u-card @click="showItem(item)" :title-color="changeBg(0)" v-for="(item,index) in articleList" :key="index" padding="20"
				box-shadow="3px 3px 10px rgba(0,0,0,0.1)" :border="false" :title="item.title" :sub-title="item.userName"
				:thumb="getIcon()">
				<view class="" slot="body"  >
					<view  class="u-body-item u-flex u-col-between u-p-t-0">
						<view class="u-body-item-title u-line-2" :style="{color:changeBg(0)}">{{item.context}}</view>
						<image @click.stop="showPic(item.imgList[0].base64)" :src="item.imgList[0].base64" mode="aspectFill"></image>
						
					</view>
				</view>
				<view class="u-flex u-row-between" slot="foot">
					<u-icon name="chat-fill" size="34" color="" label="评论"></u-icon>
					<text>{{item.createTime}}</text>
					<text v-if="userInfo.userInfo.userNo==item.userNo || userInfo.userInfo.type == 0" style="color: #DD524D;" @click="delArticle(item)">删除</text>
				</view>
			</u-card>
		</view>
		<view v-if="userInfo.token" class="add-btn" @click="addArticle" :class="btnClass">
			<u-icon name="plus" class="sc-text-shadow"></u-icon>
		</view>
	</view>
</template>

<script>
	import appRequest from "@/common/appRequestUrl.js"
	export default {
		data() {
			return {
				
				pageType: -1,
				color: -1,
				pageName: ["表白墙", "校内互助", "跳蚤市场"],
				tipCurrent: 0,
				pageTabList: [
					[{
					name: '表白'
				}, {
					name: '吐槽'
				}],
					[{
						name: '失物招领'
					}, {
						name: '其他互助'
					}]
				],
				background: {
					backgroundImage: "linear-gradient(to bottom, rgba(108,172,255,1) , rgba(141,235,255,0.8))"
				},
				articleList: [],
				tabActColor:"#3f80de",
				bgClass:"",
				btnClass:"",
				userInfo:""

			};
		},
		onLoad: function(options) {
			options.type = options.type || 0;
			if (options.type == 0) {
				this.color = this.tipCurrent == 0 ? 0 : 1;
			}
			this.changeTabBg();
			this.pageType = options.type;
			
		},
		onShow:function(){
			this.userInfo = appRequest.getUserInfo();
			let _this = this;
			let data = {
				type :Number(_this.pageType)  + 3,
				info:0,
			}
			this.getAtricle(data);
		},
		methods: {
			delArticle: function(item) {
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
			addArticle: function() {				
				////活动类型 0校级通知 1学院通知 2社团通知(info0社团  info1学生会) 3表白墙（info0 表白 info1吐槽）4失物招领（info0 失物招领 info1其他互助）5二手交易
				let type = this.pageType + 3;
				let info =  this.tipCurrent;
				let _this = this;
				uni.navigateTo({
					url: "../common/addArticle?type="+type+"&level=3&info="+info+"&name="+_this.pageName[_this.pageType]
				})
			},
			showPic:function(pic){
				uni.previewImage({
					current: 0,
					urls: [pic]
				});
			},
			clickTab:function(index){
				this.tipCurrent = index;
				if(this.pageType == 0){
					if(this.tipCurrent == 0){
						this.color = 0;
						this.changeTabBg();
						this.tabActColor = "#ff9a9e";
					}else{
						this.color = 1;
						this.changeTabBg();
						this.tabActColor = "rgba(28, 187, 180,1)";
					}
				}
				let _this = this;
				let data = {
					type :Number(_this.pageType)  + 3,
					info:_this.tipCurrent,
				}
				
				_this.getAtricle(data);
				
			},
			getAtricle:function(data){
				let _this = this;
				appRequest.request({
					method: "GET",
					data: {
						data: data
					},
					url: appRequest.getArticle,
					success: function(res) {
						_this.articleList = res.data.data;						
					},
					fail: function(res) {
				
					}
				})
			},
			getIcon: function() {
				return appRequest.baseUrl + '/img/' + (this.pageType + "" + this.tipCurrent) + '.png'
			},
			changeBg: function(type) {
				if (this.color == 0) {
					return type ? 'pink-bg' :'#ff9a9e';
				} else if (this.color == 1) {
					return  type ? 'green-bg': 'rgba(28, 187, 180,1)';
				} else {
					return type ? 'blue-bg' :"";
				}

			},
			changeTabBg: function() {
				if (this.color == 0) {
					this.tabActColor = "#ff9a9e";
					this.bgClass = "pink-bg"
					this.btnClass = "pink-btn";
					this.background.backgroundImage = 'linear-gradient(to bottom, #ff9a9e, rgba(254,207,239,0.3))';
				} else if (this.color == 1) {
					this.tabActColor = "rgba(28, 187, 180,1)";
					this.bgClass = "green-bg";
					this.btnClass = "green-btn";
					this.background.backgroundImage =
						'linear-gradient(45deg, rgba(28, 187, 180,1), rgba(141, 198, 63,0.5))';
				} else {
					this.bgClass = "blue-bg"
					this.btnClass = "blue-btn";
				}
			},showItem:function(item){
				try {
					uni.setStorageSync('article', item);
					uni.navigateTo({
						url:"../common/reviewArticle"
					})
				} catch (e) {
					// error
					uni.showToast({
						icon:"none",
						title:"数据异常"
					})
				}
			},TOADD(){
				uni.navigateTo({
					url:"./addArticle"
				})
			}
		}
	}
</script>

<style lang="scss">
	.content {
		min-height: 100vh;
		height: 100vh;
	}
	
	.pink-text{
		color: "#ff9a9e";
	}
	
	.green-text{
		color: "rgba(28, 187, 180,1)";
	}
	
	.pink-btn{
		background-color: rgba(255,154,158, 0.8);
		box-shadow: 1px 1px 6px rgba(255,154,158, 1);
	}
	
	.green-btn{
		background-color: rgba(28, 187, 180,0.8);
		box-shadow: 1px 1px 6px  rgba(28, 187, 180,1);
	}
	.blue-btn{
		background-color: rgba(108,172,255,0.8);
		box-shadow: 1px 1px 6px rgba(108,172,255,1);
			
	}
	
	.pink-bg {
		background: linear-gradient(to bottom, rgba(254, 207, 239, 0.6), rgba(254, 207, 239, 0));
	}

	.green-bg {
		background: linear-gradient(to bottom, rgba(141, 198, 63, 0.3), rgba(141, 198, 63, 0));
	}

	.blue-bg {
		background: linear-gradient(to bottom, rgba(141, 235, 255, 0.3), rgba(141, 235, 255, 0));
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

	.grid-text {
		font-size: 30rpx;
		margin-top: 4rpx;
		color: $u-type-info;
		font-weight: bold;
		text-shadow: 1px 1px 3px rgba($color: #000000, $alpha: 0.15);
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

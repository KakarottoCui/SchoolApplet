<template>
	<view class="content blue-bg">
		<u-navbar :border-bottom="false" :back-text-style="{color:'white'}" back-icon-color="white" back-text="返回"
			title-color="white" :titleBold="true" title="动态全文" :background="background">
		</u-navbar>
		<view class="sc-card">
			<u-card v-for="(item,index) in articleList" :key="index" padding="20"
				box-shadow="3px 3px 10px rgba(0,0,0,0.1)" :border="false" :title="item.title" :sub-title="item.userName"
				:thumb="getIcon()">
				<view slot="body">
					<view class="u-body-item u-flex u-col-between u-p-t-0 u-m-b-20 u-m-t-20">
						<view class="u-body-item-title">
							{{item.context}}
						</view>
					</view>
					<view v-for="(item,index) in item.imgList" :key="index" class="u-m-t-200">
						<image :src="item.base64" style="width: 100%;" mode="aspectFit"></image>
					</view>

					<view class="u-flex u-row-right">
						<text class="u-font-20 u-tips-color">{{item.deptName}}</text>
					</view>
				</view>
				<view class="u-flex u-row-between" slot="foot">
					<u-icon name="chat-fill" size="34" color="" label="评论"></u-icon>
					<text>{{item.createTime}}</text>
				</view>
			</u-card>

			<view style="background-color: white;border-radius: 10px;box-shadow: 3px 3px 10px rgba(0,0,0,0.1);"
				class="u-m-l-25 u-m-r-25 u-m-b-80">
				<view class="comment" v-for="(res, index) in commentList" :key="res.id">
					<view class="left">
						<image :src="res.base64" mode="aspectFill"></image>
					</view>
					<view class="right">
						<view class="top">
							<view class="name">{{ res.nick }}</view>
							<view v-if="userInfo.userInfo.type == 0 || res.user_no == userInfo.user_no" class="like"
								@click="delComment(res.id)">
								<view class="num">删除</view>
								<u-icon name="trash-fill" :size="30" color="#DD524D"></u-icon>
							</view>
						</view>
						<view class="content">{{ res.context }}</view>
						<view class="reply-box">
							<view class="item" v-for="(item, index2) in res.reCom" :key="item.index2">
								<view class="username u-flex u-row-between">{{ item.nick }}

									<view v-if="userInfo.userInfo.type == 0 || item.user_no == userInfo.user_no"
										class="like u-flex u-row-between" @click="delComment(item.id)">
										<view class="num">删除</view>
										<u-icon name="trash-fill" :size="30" color="#DD524D"></u-icon>
									</view>

								</view>
								<view class="text">{{ item.context }}</view>
							</view>
						</view>
						<view class="bottom">
							{{ res.createTime }}
							<view class="reply" @click="addComment(1,res)">回复</view>
						</view>
					</view>
				</view>
			</view>

		</view>

		<view v-if="userInfo.token" class="add-btn blue-bg2" @click="addComment(0,null)">
			<u-icon name="plus" class="sc-text-shadow"></u-icon>
		</view>


		<u-popup v-model="showModel" mode="center" border-radius="14" width="600">
			<view class="c-model-view u-p-25">
				<u-field style="height: 25vh;" :label="commentType==0?'评论':'回复'" type="textarea" placeholder="请输入评论内容"
					v-model="commentText">

				</u-field>

				<u-button type="primary" class="u-m-t-50" @click="submitComment"  :ripple="true">发布
					  </u-button>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import appRequest from "@/common/appRequestUrl.js"
	export default {
		data() {
			return {
				commentObj: "",
				commentType: 0,
				commentText: "",
				showModel: false,
				userInfo: "",
				background: {
					backgroundImage: "linear-gradient(to bottom, rgba(108,172,255,1) , rgba(141,235,255,0.8))"
				},
				articleList: [],
				commentList: []
			}
		},
		onLoad() {
			this.getData();
			this.userInfo = appRequest.getUserInfo();

		},
		methods: {
			submitComment: function() {

				let comment = {
					context: this.commentText,
					type: this.commentType,
					comFk: (this.commentType == 1 ? this.commentObj.id : 0),
					artFk: this.articleList[0].id
				}

				let _this = this;
				appRequest.request({
					method: "POST",
					data: {
						data: JSON.stringify(comment)
					},
					url: appRequest.addComment,
					success: function(res) {
						if (res.data.code == 200) {
							uni.showToast({
								title: "评论成功",
								icon: "success",
								success: function() {
									_this.showModel = false;
									_this.getComment(_this.articleList[0].id);
								}
							});
						} else {
							uni.showToast({
								title: res.data.msg,
								icon: "none"
							});
						}
					},
					fail: function(res) {

						uni.showToast({
							title: "发布异常",
							icon: "error"
						});

					}
				})

			},
			addComment: function(type, res) {

				console.log(type);

				this.commentObj = res || this.articleList[0];
				this.commentText = "";
				this.commentType = type;
				this.showModel = true;
			},
			getIcon: function() {
				return appRequest.baseUrl + '/img/group.png'
			},
			getData: function() {
				try {
					const value = uni.getStorageSync('article');
					this.getComment(value.id);
					if (value) {
						this.articleList = [value];
					}
				} catch (e) {
					// error
				}
			},
			getComment(id) {

				let _this = this;
				appRequest.request({
					method: "GET",
					data: {
						id: id
					},
					url: appRequest.getComment,
					success: function(res) {
						if (res.data.code == 200) {
							_this.commentList = res.data.data;
						} else {
							uni.showToast({
								title: res.data.msg,
								icon: "none"
							});
						}
					},
					fail: function(res) {

						uni.showToast({
							title: "发布异常",
							icon: "error"
						});

					}
				})

			},
			delComment(id) {
				let _this = this;
				uni.showModal({
					title: "提示",
					content: "是否删除？",
					success: function(res) {
						if (res.confirm) {
							_this.delById(id)
						}
					}
				})
			},
			delById(id) {
				let _this = this;
				appRequest.request({
					method: "GET",
					data: {
						id: id
					},
					url: appRequest.delComment,
					success: function(res) {
						if (res.data.code == 200) {
							uni.showToast({
								title: "删除成功",
								icon: "none",
								complete: function() {
									_this.getComment(_this.articleList[0].id);
								}
							});
						} else {
							uni.showToast({
								title: res.data.msg,
								icon: "none"
							});
						}
					},
					fail: function(res) {

						uni.showToast({
							title: "发布异常",
							icon: "error"
						});

					}
				})
			}
		}
	}
</script>

<style lang="scss">
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

	.blue-bg {
		background: linear-gradient(to bottom, rgba(141, 235, 255, 0.8), rgba(141, 235, 255, 0));
	}

	.blue-bg2 {
		background: linear-gradient(to bottom, rgba(141, 235, 255, 0.9), rgba(141, 235, 255, 0.6));
	}

	.add-btn {
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

	.comment {
		display: flex;
		padding: 30rpx;

		.left {
			image {
				width: 64rpx;
				height: 64rpx;
				border-radius: 50%;
				background-color: #f2f2f2;
			}
		}

		.right {
			flex: 1;
			padding-left: 20rpx;
			font-size: 30rpx;

			.top {
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-bottom: 10rpx;

				.name {
					color: #5677fc;
				}

				.like {
					display: flex;
					align-items: center;
					color: #9a9a9a;
					font-size: 26rpx;

					.num {
						margin-right: 4rpx;
						color: #9a9a9a;
					}
				}

				.highlight {
					color: #5677fc;

					.num {
						color: #5677fc;
					}
				}
			}

			.content {
				margin-bottom: 10rpx;
			}

			.reply-box {
				background-color: rgb(242, 242, 242);
				border-radius: 12rpx;

				.item {
					padding: 20rpx;
					border-bottom: solid 2rpx $u-border-color;

					.username {
						font-size: 24rpx;
						color: #999999;
					}
				}

				.all-reply {
					padding: 20rpx;
					display: flex;
					color: #5677fc;
					align-items: center;

					.more {
						margin-left: 6rpx;
					}
				}
			}

			.bottom {
				margin-top: 20rpx;
				display: flex;
				font-size: 24rpx;
				color: #9a9a9a;

				.reply {
					color: #5677fc;
					margin-left: 10rpx;
				}
			}
		}
	}
</style>

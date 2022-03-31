<template>
	 <view class="u-p-20">
		  <u-field v-model="article.title" label="标题:" icon="chat-fill" iconColor="#3f80de" placeholder="请填写标题">
		</u-field>
		  <u-field style="min-height: 40vh;height: 40vh;" v-model="article.context" label="正文:" type="textarea"
			icon="order" height="500" iconColor="#3f80de" placeholder="请填写正文"></u-field>

		  <view class="u-p-25 ">
			   <u-icon class="u-m-r-10 u-m-l-10" size="30" color="#3f80de" name="photo-fill"></u-icon>
			   <text>图片</text>
			  </view>
		  <view class="u-flex u-direction-row u-p-l-25 u-border-bottom u-p-b-25">
			          <u-image border-radius="15" @tap="ChooseImage()" class="u-image-shadow" height="180rpx"  
				width="180rpx" :src="article.picBase64" mode="aspectFill">
				           <view slot="error" style="font-size: 24rpx;" class="u-text-center">
					             <u-icon size="30" name="plus"></u-icon><br />选择图片
					            </view>
				           <view slot="loading" style="font-size: 24rpx;" class="u-text-center">
					             <u-icon size="30" name="reload"></u-icon><br />加载中
					            </view>
				          </u-image>      
			     </view>
		  <u-field v-if="article.type < 3" @click="show = !show" :disabled="true" v-model="selValue" label="部门:"
			icon="list" iconColor="#3f80de" placeholder="请选择部门"></u-field>


		  <u-select @confirm="selConfirm" v-model="show" :list="deptList"></u-select>
		  
		  <u-button type="primary" class="u-m-t-50" @click="submit"    :ripple="true">发布
			  </u-button>
		  
		 </view>
</template>

<script>
	import appRequest from "@/common/appRequestUrl.js"
	export default {
		data() {
			return {
				//活动类型 0校级通知 1学院通知 2社团通知(info0社团  info1学生会) 3表白墙（info0 表白 info1吐槽）4失物招领（info0 失物招领 info1其他互助）5二手交易
				article: {
					title: "",
					context: "",
					type: "",
					info: "",
					level: "",
					pic: 0,
					picBase64: "",
					deptFk:""
				},
				show: false,
				type: 0, //type 0管理员，1普通,2社团管理员
				model: 0, //0校级通知，1院系通知，2社团通知，3其他，4表白，5吐槽，6失物招领，7其他互助，9闲置出售
				deptList: [{
						value: '1',
						label: '江'
					},
					{
						value: '2',
						label: '湖'
					}
				],
				selValue: "",
				userName: "测试",
				imgList: [{
					base64: ""
				}],
				selector: [],
				picN: "",
				//文章等级，0校级 1学院级 2社团级 3个人级
			};
		},
		onLoad(options) {
			this.article.type = options.type;
			this.article.info = options.info || 0;
			this.article.level = options.level;
			if (this.article.type < 3) {
				this.getDeptInfo();
			}
			
			 let userInfo = appRequest.getUserInfo();
			
			if((this.article.type < 2 && userInfo.userInfo.type != 0) || ( this.article.type == 2 && userInfo.userInfo.type == 1 )){
				uni.showModal({
					title:"权限异常",
					content:"仅限管理员添加",
					showCancel:false,
					success:function(res){
						if(res.confirm){
							uni.navigateBack({
								
							})
						}
					}
				})
			}
			
			
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
				let _this = this;
				uni.navigateTo({
					url: "../common/addArticle?type=2&level=2"
				})
			},
			getDeptInfo: function() {
				let _this = this;
				appRequest.request({
					method: "GET",
					url: appRequest.getDeptData,
					success: function(res) {
						_this.deptList = [];
						res.data.data.map(function(item, index, arr) {
							if(_this.article.level == item.level){
								_this.deptList.push({
									value: item.id,
									label: item.name
								})
							}
						});
					},
					fail: function(res) {

					}
				})
			},
			ChooseImage() {
				let _this = this;
				uni.chooseImage({
					count: 1,
					sizeType: ["compressed"],
					success: function(res) {
						let src = res.tempFiles[0].path;
						uni.compressImage({
							src: src,
							width: 450,
							quality: 70,
							success: function(path) {
								let filePath = path.tempFilePath;
								wx.getFileSystemManager().readFile({
									filePath: filePath,
									encoding: 'base64',
									fail: function(errMsg) {
										console.log(errMsg);
									},
									success: function(res) {
										_this.article.picBase64 =
											"data:image/jpeg;base64," + res.data;
									}
								});
							}
						});
					},
					fail: function(errMsg) {
						console.log(errMsg);
					}
				});
			},
			selConfirm(e) {
				this.article.deptFk = e[0].value;
				this.selValue = e[0].label;
			},
			submit() {
				let _this = this;
				if (_this.article.title == "" || _this.article.context == "") {
					uni.showToast({
						title: "请填写完整标题和正文",
						icon: "none"
					});
					return;
				}

				_this.article.pic = _this.article.picBase64 ? 1 : 0;
				
				if(_this.article.type < 3 && !_this.article.deptFk){
					uni.showToast({
						title: "部门通知需要选择部门",
						icon: "none"
					});
					return;
				}
				
				uni.showModal({
					title: "发布内容",
					content: "是否发布当前内容？",
					success: function(res) {
						if (res.confirm) {
							_this.addAtricle()
						}
					}
				})

			},
			addAtricle: function() {
				let _this = this;
				appRequest.request({
					method: "POST",
					data: {
						data: JSON.stringify(_this.article)
					},
					url: appRequest.addArticle,
					success: function(res) {
						if (res.data.code == 200) {
							uni.showToast({
								title: "发布成功",
								icon: "success",
								success: function() {
									uni.navigateBack({});
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

</style>

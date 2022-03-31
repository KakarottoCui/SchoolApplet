<template>
	<view class="content">
		<view :class="topNavStyle.class" :style="topNavStyle.style" class="top-back"></view>
		<view class="page" style="text-align: center;padding: 23% 5% 5px 5%;z-index: 100;">
			<view class="u-font-40 sc-text-shadow sc-text-bold" style="color: #007AFF;">互动消息</view>
		</view>
		<view class="tip" style="padding: 2% 5% 0 5%; display: flex; justify-content: center;">
			<u-tabs bg-color="rgba(255,255,255,0)" :list="tipList" :is-scroll="true" :current="tipCurrent" @change="tabChange"></u-tabs>
			<!-- <u-tabs active-color="#102b6a" inactive-color="white" :list="tipList" :current="tipCurrent" @change="change"></u-tabs> -->
			<!-- <u-tabs lineColor="#102b6a" :inactiveStyle="{color:'white'}" :activeStyle="{color:'#102b6a'}" :list="tipList" @click="click"></u-tabs> -->
		</view>
		
		<view class="u-p-25">
			<view v-if="tipCurrent == 0">
				
				<view v-if="articleList.length == 0" class="u-m-t-80">
					<u-empty text="暂无数据" mode="message"></u-empty>
				</view>
				
				<u-cell-group :border="false">
						<u-cell-item :arrow="false"  v-for="(item,index) in articleList" :key="index" icon="list-dot" :label="item.context" :title="item.title" ></u-cell-item>
				</u-cell-group>
				
			</view>
			<view v-if="tipCurrent == 1">
				
				<view v-if="commentList.length == 0" class="u-m-t-80">
					<u-empty text="暂无数据" mode="message" ></u-empty>
				</view>
				
				<u-cell-group :border="false">
						<u-cell-item :arrow="false"  v-for="(item,index) in commentList" :key="index" icon="list-dot" :title="item.context" :value="item.nick" :label="item.createTime"  ></u-cell-item>
				</u-cell-group>
				
			</view>
			
			<view v-if="tipCurrent == 2">
				
				<view v-if="otherComment.length == 0" class="u-m-t-80">
					<u-empty text="暂无数据" mode="message" ></u-empty>
				</view>
				
				<u-cell-group :border="false">
						<u-cell-item :arrow="false" v-for="(item,index) in otherComment" :key="index" icon="list-dot" :title="'标题:'+item.context" :value="item.nick" :label="'评论'+item.context"  ></u-cell-item>
				</u-cell-group>
				
			</view>
		</view>
		
		
	</view>
</template>

<script>
	import appRequest from "@/common/appRequestUrl.js"
	export default {
		data() {
			return {
				tipList: [{
					name: '我发布的'
				}, {
					name: '我评论的'
				},{
					name: '回复我的'
				}],
				tipCurrent: 0,
				userInfo:"",
				articleList:[],
				commentList:[],
				otherComment:[]
			};
		},computed: {
			
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
		onLoad:function(){
			this.userInfo = appRequest.getUserInfo();
			this.getData();
		},
		methods:{
			tabChange:function(index){
				this.tipCurrent = index;
				this.getData();
			},
			getData(){
				console.log(this.userInfo.token);
				if(this.userInfo.token){
					let _this = this;
					let url = "";
					switch (_this.tipCurrent){
						case 0:
							url = appRequest.getArticleByUserNo;
							break;
						case 1:
							url = appRequest.getCommentByUserAndType;
							break;
						case 2:
							url = appRequest.queryByAritcleParam;
							break;		
						default:
							break;
					}
							console.log(url)
					appRequest.request({
						method: "GET",
						url: url,
						data:{
							type:0
						},
						success: function(res) {
							if (res.data.code == 200) {
								switch (_this.tipCurrent){
									case 0:
										 _this.articleList = res.data.data;
										break;
									case 1:
										_this.commentList= res.data.data;
										break;
									case 2:
										_this.otherComment= res.data.data;
										break;		
									default:
										break;
								}
								
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
	}
</script>

<style lang="scss">
	.top-back{
		position: fixed;
		top: 0;
		left: 0;
		height: 20vh;
		width: 100vw; 
		//background: linear-gradient(to right, #6cacff 0%, #8debff 100%);
		background: linear-gradient(to bottom, rgba(108,172,255,1) , rgba(141,235,255,1), rgba(108,172,255,0) ,rgba(141,235,255,0));
		z-index: 0;
	}
	.content {
		min-height: 100vh;
		background-color: rgba(144,215,236,0.1);
		
	}
	.u-cell__label{
		    overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
			width: 60vw;
	}
	.u-cell__title{
		    overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
	}
	.u-cell__value{
		    overflow: hidden;
		    text-overflow: ellipsis;
		    white-space: nowrap;
	}

</style>

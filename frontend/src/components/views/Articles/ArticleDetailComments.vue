<template>
  <span>
    <h1 class="comment-title" v-if="comments.length > 0">
      <i class="fa fa fa-comments-o fa-lg" aria-hidden="true"></i>
      Comments
    </h1>

    <div class="row" v-for="comment in comments" :key="comment.commentNo">
      <!-- question -->
      <div v-if="parseInt(comment.insertUser) !== parseInt(article.insertUser)">
        <div class="col-xs-12" >
          <article-detail-comments-editor :comment="comment" :article="article" />
          &nbsp;
          <article-detail-comments-button :comment="comment" :article="article" />
        </div>
        <div class="col-xs-12 question_Box" v-if="comment.commentStatus != 1">
          <div class="question_image">
            <img v-lazy="comment.insertUserIcon" alt="icon" width="64" height="64" />
          </div>
          <div class="arrow_question markdown">
            <article-detail-comments-contents :comment="comment" :article="article" />
          </div>
        </div>
        <div class="text-left collapse_comment" v-if="comment.commentStatus == 1">
          {{$t('ArticleDetailComments.DisplayHidden')}}
          <br />
        </div>
      </div>
      <!-- answer -->
      <div v-if="parseInt(comment.insertUser) === parseInt(article.insertUser)">
        <div class="col-xs-12 text-right">
          <article-detail-comments-button :comment="comment" :article="article" />
          &nbsp;
          <article-detail-comments-editor :comment="comment" :article="article" />
        </div>
        <div class="col-xs-12 question_Box" v-if="comment.commentStatus != 1">
          <div class="answer_image">
            <img v-lazy="comment.insertUserIcon" alt="icon" width="64" height="64" />
          </div>
          <div class="arrow_answer markdown">
            <article-detail-comments-contents :comment="comment" :article="article" />
          </div>
        </div>
        <div class="text-right collapse_comment" v-if="comment.commentStatus == 1">
          {{$t('ArticleDetailComments.DisplayHidden')}}
          <br />
        </div>
      </div>
    </div>
  </span>
</template>

<script>
import ArticleDetailCommentsEditor from './ArticleDetailCommentsEditor'
import ArticleDetailCommentsButton from './ArticleDetailCommentsButton'
import ArticleDetailCommentsContents from './ArticleDetailCommentsContents'

export default {
  name: 'ArticleDetailComments',
  props: ['comments', 'article'],
  components: { ArticleDetailCommentsEditor, ArticleDetailCommentsButton, ArticleDetailCommentsContents },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>
.comment-title {
  font-size: 24px;
}
</style>

<style src="../../css/comments.css" />

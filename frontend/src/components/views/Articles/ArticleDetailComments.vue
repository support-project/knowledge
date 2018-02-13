<template>
  <span>
    <ul class="timeline commentsArea">
      <!-- timeline time label -->
      <li class="time-label">
        <span class="bg-gray">
          <i class="fa fa fa-comments-o fa-lg" aria-hidden="true"></i>
          Comments
          <!-- {{comments[0].insertDatetime | dispDate}} -->
        </span>
      </li>
      <!-- /.timeline-label -->
      <li v-if="comments.length === 0">
        <i class="fa fa-comments"></i>
        <div class="timeline-item">
          <span class="time"><i class="fa fa-clock-o"></i></span>
          <!-- <h3 class="timeline-header"></h3> -->
          <div class="timeline-body link-muted">
            {{$t('ArticleDetailComments.NotYetBeenRegistered')}}
          </div>
          <!-- <div class="timeline-footer"></div> -->
        </div>
      </li>
      <!-- timeline item -->
      <li v-for="comment in comments" :key="comment.commentNo">
        <i class="fa fa-comments" 
          v-bind:class="{
            'bg-blue': parseInt(comment.insertUser) !== parseInt(article.insertUser),
            'bg-green': parseInt(comment.insertUser) === parseInt(article.insertUser),
          }"
        ></i>
        <div class="timeline-item">
          <span class="time"><i class="fa fa-clock-o"></i>
            {{comment.insertDatetime | dispDate}}
            ({{$t('Label.Create')}})
            <span v-if="comment.insertDatetime !== comment.updateDatetime">
              / {{comment.updateDatetime | dispDate}}
              ({{$t('Label.Update')}})
            </span>
          </span>
          <h3 class="timeline-header">
            <img v-lazy="comment.insertUserIcon" alt="icon" width="18" height="18" />
            <a href="#">{{comment.insertUserName}}</a>
          </h3>

          <div class="timeline-body">
            <article-detail-comments-contents :comment="comment" :article="article" />
          </div>
          <div class="timeline-footer">
            <article-detail-comments-button :comment="comment" :article="article" />
          </div>
        </div>
      </li>
      <!-- END timeline item -->

      <!-- timeline time label -->
      <li class="time-label">
        <span class="bg-gray">
          <i class="fa fa fa-comment-o fa-lg" aria-hidden="true"></i>
          Add Comment
          <!-- {{comments[0].insertDatetime | dispDate}} -->
        </span>
      </li>
      <!-- /.timeline-label -->
      
      <!-- timeline edit item -->
      <li>
        <i class="fa fa-edit" 
          v-bind:class="{
            'bg-teal': parseInt(user.userId) !== parseInt(article.insertUser),
            'bg-olive': parseInt(user.userId) === parseInt(article.insertUser),
          }"
        ></i>
        <div class="timeline-item">
          <span class="time"><i class="fa fa-clock-o"></i>
            {{now | dispDate}}
          </span>
          <h3 class="timeline-header">
            <img v-lazy="user.avatar" alt="icon" width="18" height="18" />
            <a href="#">{{user.userName}}</a>
          </h3>

          <div class="timeline-body">
            <markdown-editor :article="comment" :rows="10" ref="commentEditor"/>
          </div>
          <div class="timeline-footer">
            <a class="btn btn-primary btn-xs" v-on:click="addComment(article.knowledgeId, comment)">
              <i class="fa fa-save"></i>
              {{$t('Label.Release')}}
            </a>
          </div>
        </div>
      </li>
      <!-- END timeline edit item -->
      <li>
        <i class="fa fa-clock-o bg-gray"></i>
      </li>
    </ul>
  </span>
</template>

<script>
import { mapState } from 'vuex'
import ArticleDetailCommentsEditor from './ArticleDetailCommentsEditor'
import ArticleDetailCommentsButton from './ArticleDetailCommentsButton'
import ArticleDetailCommentsContents from './ArticleDetailCommentsContents'
import MarkdownEditor from '../Parts/MarkdownEditor'

export default {
  name: 'ArticleDetailComments',
  props: ['comments', 'article'],
  components: { ArticleDetailCommentsEditor, ArticleDetailCommentsButton, ArticleDetailCommentsContents, MarkdownEditor },
  data: function () {
    return {
      // section: 'Dash',
      now: new Date(),
      comment: {
        content: '',
        displaySafeHtml: ''
      }
    }
  },
  computed: {
    ...mapState({
      user: state => state.user.user
    })
  },
  methods: {
    addComment: function (id, comment) {
      this.$store.dispatch('comments/addComment', {
        id: id,
        comment: comment
      }).then(() => {
        this.comment = {
          content: '',
          displaySafeHtml: ''
        }
        this.$refs.commentEditor.clearPreview()
      })
    }
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>
.comment-title, .time-label {
  font-size: 18px;
}
.commentsArea {
  margin-top: 20px;
}
</style>

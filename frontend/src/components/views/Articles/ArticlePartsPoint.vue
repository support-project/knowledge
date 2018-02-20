<template>

  <span class="">
    <span class="break-line-block">
      <i class="fa fa-heart-o" aria-hidden="true"></i>&nbsp;CP × {{article.point}}
    </span>

    <span class="article-point-value break-line-block" v-if="article.pointOnTerm"> <!-- only popular list -->
      <i class="fa fa-line-chart" aria-hidden="true"></i>&nbsp;× {{article.pointOnTerm}} &nbsp;
    </span>

    <span class="article-point-value break-line-block">
      <button class="text-primary btn btn-link" v-on:click="showLikes(article.knowledgeId)">
        <i class="fa fa-thumbs-o-up"></i>&nbsp;Like × <span id="like_count">{{article.likeCount}}</span>
      </button>
    </span>

    <span class="article-point-value break-line-block">
      <a href="#comments" id="commentsLink" class="text-primary btn-link inner-page-link">
        <i class="fa fa-comments-o"></i>&nbsp;Comments × {{article.commentCount}}
      </a>
    </span>

    <span class="article-point-value break-line-block">
      <a href="#attachFilesPanel" id="attachFilesLink" class="text-primary btn-link inner-page-link" v-if="article.attachments">
        <i class="fa fa-download"></i>&nbsp;Attach × {{article.attachments ? article.attachments.length : 0 }}
      </a>
    </span>
    
    <!-- 参加者数
    <span class="article-point-value break-line-block">
      <a id="eventInfoLink" class="text-primary btn-link" v-if="article.typeId === -101">
        <i class="fa fa-users"></i>&nbsp;participants × {{article.commentCount}}
      </a>
    </span>
    -->

    <likes-modal />
  </span>

</template>

<script>
/* global $ */
import LikesModal from './LikesModal'
export default {
  name: 'ArticlePartsPoint',
  props: ['article'],
  components: { LikesModal },
  methods: {
    showLikes: function (id) {
      this.$store.dispatch('likes/showLikesModal', id)
    }
  },
  mounted () {
    this.$nextTick(() => {
      $('.inner-page-link').click(function () {
        var speed = 500
        var href = $(this).attr('href')
        var target = $(href)
        if (target && target.offset && target.offset()) {
          var position = target.offset().top
          position = position - 100
          $('html, body').animate({scrollTop: position}, speed, 'swing')
        }
        return false
      })
    })
  }
}
</script>

<style>
.article-point-value {
  height: 10px;
}
.break-line-block .btn-link {
  padding: 0px;
  margin-right: 5px;
}
</style>

<template>
  <div>
    <div class="knowledge_item" v-for="article in articles">
      <!-- article and editor information -->
      <div >
        <router-link tag="a" :to="'/articles/' + article.knowledgeId">
          <a>
            <div class="list-title">
              <span class="dispKnowledgeId">
                #{{article.knowledgeId}}
              </span>
              {{article.title}}
            </div>
          </a>
        </router-link>
      </div>

      <div class="item-info">
        <article-parts-editor :article="article" />
      </div>

      <div >
        <div class="item-info">
          <i class="fa fa-heart-o"></i>&nbsp;× {{article.point}}
          <span v-if="article.pointOnTerm"> <!-- only popular list -->
            <i class="fa fa-line-chart" aria-hidden="true"></i>&nbsp;× {{article.pointOnTerm}} &nbsp;
          </span>
          <button class="text-primary btn btn-link" v-on:click="showLikes(article.knowledgeId)">
              <i class="fa fa-thumbs-o-up"></i>&nbsp;× <span id="like_count">{{article.likeCount}}</span>
          </button>
          <router-link tag="a" :to="'/articles/' + article.knowledgeId + '#comments'" class="text-primary btn-link">
            <i class="fa fa-comments-o"></i>&nbsp;× {{article.commentCount}}
          </router-link>
          
          <article-parts-type-label :article="article" />
          <article-parts-public-flag :article="article" />

        </div>
      </div>
      <div >
        <div class="item-info">
          <article-parts-tags :article="article" />
          <article-parts-stocks :article="article" />
        </div>
      </div>
      <div class="item-info" v-if="article.highlightedTitle || article.highlightedContents">
        <span v-html="article.highlightedTitle" v-if="article.highlightedTitle"></span>
        <span v-html="article.highlightedContents" v-if="article.highlightedContents"></span>
      </div>
    </div>

    <likes-modal />
  </div>

<!-- TODO event information if this article type is event -->

</template>
<script>
import ArticlePartsEditor from './ArticlePartsEditor'
import ArticlePartsPublicFlag from './ArticlePartsPublicFlag'
import ArticlePartsTags from './ArticlePartsTags'
import ArticlePartsStocks from './ArticlePartsStocks'
import ArticlePartsTypeLabel from './ArticlePartsTypeLabel'
import LikesModal from './LikesModal'
export default {
  name: 'ArticleListItem',
  props: ['articles'],
  components: { ArticlePartsEditor, ArticlePartsPublicFlag, ArticlePartsTags, ArticlePartsStocks, ArticlePartsTypeLabel, LikesModal },
  methods: {
    showLikes: function (id) {
      this.$store.dispatch('likes/showLikesModal', id)
    }
  },
  mounted () {
  }
}
</script>

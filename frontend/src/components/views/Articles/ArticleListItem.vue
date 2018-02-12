<template>
  <div>
    <div class="knowledge_item" v-for="article in articles" :key="article.knowledgeId">
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
          <i class="fa fa-heart-o"></i>&nbsp;× {{article.point}} &nbsp;
          <span v-if="article.pointOnTerm"> <!-- only popular list -->
            <i class="fa fa-line-chart" aria-hidden="true"></i>&nbsp;× {{article.pointOnTerm}} &nbsp;
          </span>
          <a :href="'open.knowledge/likes/' + article.knowledgeId" class="text-primary btn-link">
              <i class="fa fa-thumbs-o-up"></i>&nbsp;× <span id="like_count">{{article.likeCount}}</span>
          </a> &nbsp;
          <a :href="'open.knowledge/view/' + article.knowledgeId + '#comments'" class="text-primary btn-link">
              <i class="fa fa-comments-o"></i>&nbsp;× {{article.commentCount}}
          </a> &nbsp;
          <i :class="'fa ' + article.type.icon"></i>&nbsp;{{article.type.name}}
          
          <article-parts-public-flag :article="article" />
        </div>
      </div>

      <div >
        <div class="item-info">
          <article-parts-tags :article="article" />
          <article-parts-stocks :article="article" />
        </div>
      </div>

    </div>
  </div>

<!-- TODO event information if this article type is event -->

</template>
<script>
import ArticlePartsEditor from './ArticlePartsEditor'
import ArticlePartsPublicFlag from './ArticlePartsPublicFlag'
import ArticlePartsTags from './ArticlePartsTags'
import ArticlePartsStocks from './ArticlePartsStocks'
export default {
  name: 'ArticleListItem',
  props: ['articles'],
  components: { ArticlePartsEditor, ArticlePartsPublicFlag, ArticlePartsTags, ArticlePartsStocks },
  mounted () {
  }
}
</script>
<style>
.update_info {
  margin-left: 10px;
}
</style>

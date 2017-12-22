<template>
  <div>
    <div class="knowledge_item" v-for="article in articles" :key="article.knowledgeId">
      <!-- article and editor information -->
      <div class="insert_info">
        <a href="open.knowledge/view/1" class="text-primary btn-link">
          <div class="list-title">
            <span class="dispKnowledgeId">
              #{{article.knowledgeId}}
            </span>
            {{article.title}}
          </div>
        </a>
        <div class="item-info">
          <a :href="'open.account/info/' + article.insertUser" class="text-primary btn-link">
            <img v-lazy="article.insertUserIcon" alt="icon" width="18" height="18" />
            {{article.insertUserName}}
          </a>
          -
          <i class="fa fa-calendar-plus-o" aria-hidden="true"></i>&nbsp;{{article.insertDatetime | moment}}
          <span v-if="article.insertDatetime != article.updateDatetime" class="update_info">
            <i class="fa fa-angle-double-right" aria-hidden="true"></i>
            <a :href="'open.account/info/' + article.updateUser" class="text-primary btn-link">
              <img v-lazy="article.updateUserIcon" alt="icon" width="18" height="18" />
              {{article.updateUserName}}
            </a>
            -
            <i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp;{{article.updateDatetime | moment}}
          </span>
        </div>
      </div>
      <!-- article attribute information -->
      <div class="item-info">
        <i class="fa fa-heart-o" style="margin-left: 5px;"></i>&nbsp;× {{article.point}} &nbsp;
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
        
        <i class="fa fa-globe" v-if="article.publicFlag === 0"></i>
        <i class="fa fa fa-lock" v-if="article.publicFlag === 1"></i>
        <i class="fa fa-gavel" v-if="article.publicFlag === 2"></i>
        {{ $t('Article.PublicFlag.' + article.publicFlag) }}

        <!-- 保護の場合のターゲット -->
        <span v-if="article.viewers">
          <span v-for="group in article.viewers.groups" :key="group.id">
            <a :href="'open.knowledge/list?group=' + group.id">
              <span class="tag label label-default">
                <i class="fa fa-users"></i>{{group.name}}
              </span>
            </a>
          </span>
          <span v-for="user in article.viewers.users" :key="user.id">
            <a :href="'open.knowledge/list?user=' + user.id">
              <span class="tag label label-default">
                <i class="fa fa-user"></i>{{user.name}}
              </span>
            </a>
          </span>
        </span>
        <span v-if="article.tags && article.tags.length > 0">
          <i class="fa fa-tags"></i>
          <span v-for="tag in article.tags" :key="tag">
            <a :href="'open.knowledge/list?tagNames=' + tag" v-if="tag">
                <span class="tag label label-default"><i class="fa fa-tag"></i>{{tag}}</span>
            </a>&nbsp;
          </span>
        </span>
        <span v-if="article.stocks && article.stocks.length > 0">
          <i class="fa fa-star-o"></i>
          <span v-for="stock in article.stocks" :key="stock.stockId">
            <a :href="'open.knowledge/stocks?stockid=' + stock.stockId">
                <span class="tag label label-default"><i class="fa fa-star"></i>{{stock.stockName}}</span>
            </a>&nbsp;
          </span>
        </span>


      </div>
    <!-- article list end -->
    </div>






  </div>


<!-- TODO event information if this article type is event -->
<!--
                 <% if (knowledge.getStartDateTime() != null) { %>
                 <div>
                     <i class="fa fa-calendar"></i>&nbsp;
                     <%= jspUtil.label("knowledge.list.event.datetime") %>: <%= knowledge.getLocalStartDateTime(jspUtil.locale(), timezone) %>
                     <%
                         if (stock != null && stock.getParticipations() != null) {
                     %>
                     <i class="fa fa-users"></i>&nbsp;<%= stock.getParticipations().getCount() %> /  <%= stock.getParticipations().getLimit() %>
                     <% if (stock.getParticipations().getStatus() != null) { %>
                     <span class="badge">
                         <% if (stock.getParticipations().getStatus() == EventsLogic.STATUS_PARTICIPATION) { %>
                            <%= jspUtil.label("knowledge.view.label.status.participation") %>
                         <% } else { %>
                            <%= jspUtil.label("knowledge.view.label.status.wait.cansel") %>
                         <% } %>
                     </span>
                     <% } %>
                     <% } %>
                 </div>
                 <% } %>
            </div>
-->

</template>
<script>
import moment from 'moment'
export default {
  name: 'ArticleListItem',
  props: ['articles'],
  filters: {
    moment: function (date) {
      return moment(date).format('YYYY/MM/DD HH:mm')
    }
  },
  mounted () {
  }
}
</script>
<style>
.update_info {
  margin-left: 10px;
}
.tag {
  margin-right: 5px;
}
</style>

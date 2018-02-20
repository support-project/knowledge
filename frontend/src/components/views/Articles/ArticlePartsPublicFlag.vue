<template>
  <span>
    <!-- <i :class="'fa ' + article.type.icon"></i>&nbsp;{{article.type.name}} -->
    
    <span v-if="article.publicFlag !== undefined">
      <i class="fa fa-globe" v-if="article.publicFlag === 0"></i>
      <i class="fa fa-lock" v-if="article.publicFlag === 1"></i>
      <i class="fa fa-address-book-o" v-if="article.publicFlag === 2"></i>
      {{ $t('Article.PublicFlag.' + article.publicFlag) }}
    </span>

    <!-- 保護の場合のターゲット -->
    <span v-if="article.viewers">
      <span v-for="group in article.viewers.groups" :key="group.id">
        <a :href="'open.knowledge/list?group=' + group.id">
          <span class="tag label label-default">
            <i class="fa fa-users"></i>{{group.name}}
          </span>
        </a>
      </span>

      <span v-for="user in article.viewers.users"> <!-- :key="user.id" -->
        <a :href="'open.knowledge/list?user=' + user.id">
          <span class="tag label label-default">
            <i class="fa fa-user"></i>{{user.name}}
          </span>
        </a>
      </span>
    </span>
  </span>
</template>

<script>
export default {
  name: 'ArticlePartsPublicFlag',
  props: ['article'],
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>


<template>
  <span v-if="article.insertUser">
    <span class="break-line-block">
    <a :href="'open.account/info/' + article.insertUser" class="text-primary btn-link">
      <img v-lazy="article.insertUserIcon" alt="icon" width="18" height="18" />
      {{article.insertUserName}}
    </a>
    -
    <i class="fa fa-calendar-plus-o" aria-hidden="true"></i>&nbsp;{{article.insertDatetime | dispDate}}
    </span>

    <span v-if="article.insertDatetime !== article.updateDatetime" class="update_info">
      <i class="fa fa-angle-double-right" aria-hidden="true"></i>
      <span class="break-line-block">
        <a :href="'open.account/info/' + article.updateUser" class="text-primary btn-link">
          <img v-lazy="article.updateUserIcon" alt="icon" width="18" height="18" />
          {{article.updateUserName}}
        </a>
        -
        <i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp;{{article.updateDatetime | dispDate}}
      </span>
    </span>
  </span>
</template>

<script>
export default {
  name: 'ArticlePartsEditor',
  props: ['article'],
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>
<style>
.update_info {
  margin-left: 10px;
}
</style>

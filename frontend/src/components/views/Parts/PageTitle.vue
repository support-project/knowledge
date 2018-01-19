<template>
  <!-- Content Header (Page header) -->
  <section class="content-header left-margin-content">
    <h1>
      {{ $t(title) }}
      <small>
        {{ $t(description) }}
      </small>
    </h1>
    <ol class="breadcrumb">
      <router-link tag="li" class="pageLink" to="/">
        <a>
          <i class="fa fa-home"></i>Home
        </a>
      </router-link>
      <router-link tag="li" class="pageLink" v-for="(item) in breadcrumb" :key="item.name" :to="item.to">
        <a>
          {{ $t(item.name) }}
        </a>
      </router-link>
    </ol>
  </section>
</template>

<script>
export default {
  name: 'PageTitle',
  props: ['title', 'description', 'breadcrumb']
}
</script>

<style>
.content-header h1 {
  margin-bottom: 15px;
}
</style>

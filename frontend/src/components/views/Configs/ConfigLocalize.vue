<template>

  <div>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <page-title
        :title = "'Route.' + $route.name"
        :description = "$route.name + '.description'"
        :breadcrumb = "breadcrumb" />

      <!-- Main content -->
      <div class="content main-content">

        <div class="box box-info">
          <!-- header -->
          <div class="box-header with-border">
            <h3 class="box-title"></h3>
          </div>

          <!-- form start(main) -->
          <div class="form-horizontal">
            <div class="box-body">

              <div class="form-group">
                <label for="" class="col-sm-2 control-label">{{$t('ConfigLocalize.Language')}}:</label>
                <div class="col-sm-10">
                  <span v-if="!editLanguage">
                    <span v-if="$i18n.locale == 'ja'">
                      {{$t('ConfigLocalize.Japanese')}}
                    </span>
                    <span v-if="$i18n.locale != 'ja'">
                      {{$t('ConfigLocalize.English')}}
                    </span>
                    <button type="button" class="btn btn-default" v-on:click.prevent="doEditLanguage()">
                      {{$t('ConfigLocalize.Edit')}}
                    </button>
                  </span>
                  <span v-if="editLanguage">
                    <button type="button" class="btn btn-default" v-on:click.prevent="changeLanguage('en')">
                      {{$t('ConfigLocalize.English')}}
                    </button>
                    <button type="button" class="btn btn-default" v-on:click.prevent="changeLanguage('ja')">
                      {{$t('ConfigLocalize.Japanese')}}
                    </button>
                  </span>
                </div>
              </div>

              <div class="form-group">
                <label for="" class="col-sm-2 control-label">{{$t('ConfigLocalize.Timezone')}}:</label>
                <div class="col-sm-10">
                  <span v-if="!editTimezone">
                    {{user.timezone}}
                    <button type="button" class="btn btn-default" v-on:click.prevent="doEditTimezone()">
                      {{$t('ConfigLocalize.Edit')}}
                    </button>
                  </span>
                  <span v-if="editTimezone">
                    <div class="input-group input-group-sm">
                      <input type="text" class="form-control" id="timezone" v-model="user.timezone" autocomplete="off">
                      <span class="input-group-btn">
                        <button type="button" class="btn btn-info btn-flat" v-on:click.prevent="doneEditTimezone()">OK</button>
                      </span>
                    </div>
                    <typeahead v-model="user.timezone" :data="timezonedata" target="#timezone" />
                  </span>
                </div>
              </div>
            </div>

            <!-- /.box-body -->
            <div class="box-footer">
            </div>
            <!-- /.box-footer -->
          </div>
        </div>

      </div>
    </div>
    <!-- /.content-wrapper -->
  </div>
</template>

<script>
import { mapState } from 'vuex'
import PageTitle from '../Parts/PageTitle'
import timezones from 'moment-timezone/data/meta/latest.json'
import {Typeahead} from 'uiv'

export default {
  name: 'ConfigLocalize',
  components: { PageTitle, Typeahead },
  data () {
    var timezonedata = []
    for (var zone in timezones.zones) {
      timezonedata.push(zone)
    }
    return {
      breadcrumb: [
        {to: '/lang', name: 'Route.ConfigLocalize'}
      ],
      editLanguage: false,
      editTimezone: false,
      timezonedata: timezonedata
    }
  },
  computed: {
    ...mapState({
      pagestate: state => state.pagestate,
      user: state => state.user.user
    })
  },
  methods: {
    changeLanguage (lang) {
      this.$store.dispatch('user/setLanguage', {
        $i18n: this.$i18n,
        lang: lang
      }).then(() => {
        this.editLanguage = false
      })
    },
    doEditLanguage () {
      this.editLanguage = true
    },
    doEditTimezone () {
      this.editTimezone = true
    },
    doneEditTimezone () {
      this.editTimezone = false
    }
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>

</style>

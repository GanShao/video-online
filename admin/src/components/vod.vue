<template>
  <!--视频点播不支持追加上传，所以这里默认一个分片大小1个G-->
  <big-file v-bind:input-id="inputId"
            v-bind:text="text"
            v-bind:suffixs="suffixs"
            v-bind:use="use"
            v-bind:after-upload="afterUpload"
            v-bind:shard-size="1024 * 1024 * 1000"
            v-bind:url="'vod'"></big-file>
</template>

<script>
    import BigFile from "./big-file";

    export default {
        components: {BigFile},
        name: 'vod',
        props: {
            text: {
                default: "上传VOD"
            },
            inputId: {
                default: "vod-upload"
            },
            suffixs: {
                default: []
            },
            use: {
                default: ""
            },
            afterUpload: {
                type: Function,
                default: null
            },
        },
        methods: {}
    }
</script>

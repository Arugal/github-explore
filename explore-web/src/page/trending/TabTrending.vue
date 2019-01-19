<template>
  <el-container direction="vertical">
    <el-header style="height:20%">
      <h1>Trending</h1>
      <p>See what the Github community is most excited about {{timeCodeValue}}.</p>
    </el-header>
    <el-main style="height:80%;">
      <div id="trending-div" class="explore-content">
        <el-select v-model="languageCodeValue" @change="languageCodeChange()">
          <el-option
            v-for="languageCode in languageCodes"
            :key="languageCode.name"
            :label="languageCode.aliasName"
            :value="languageCode.name"
          ></el-option>
        </el-select>
        <el-tabs v-model="timeCodeValue" value="weekly" @tab-click="handleClick">
          <el-tab-pane
            :key="item.name"
            :label="item.aliasName"
            :name="item.name"
            v-for="(item) in timeCodes"
          >
            <ul class="trending-ul repo-list">
              <li v-for="trending in trendings" class="trending-li">
                <div class="d-inline-block col-9 mb-1">
                  <h3>
                    <a
                      :href="'https://github.com/'+trending.repositorie.owner+'/'+trending.repositorie.name"
                      target="_blank"
                    >
                      <span>{{trending.repositorie.owner}} /</span>
                      {{trending.repositorie.name}}
                    </a>
                  </h3>
                </div>
                <div>
                  <p
                    class="col-9 d-inline-block text-gray m-0 pr-4"
                    v-html="trending.repositorie.describe"
                  ></p>
                </div>
                <div class="f6 text-gray mt-2">
                  <span class="d-inline-block mr-3">
                    <span
                      class="repo-language-color ml-0"
                      v-bind:style="languageSpan"
                      style="position: relative; top:-0.5px"
                    ></span>
                    <span itemprop="programmingLanguage">{{languageCodeAliasName}}</span>
                  </span>
                  
                  <a
                    class="muted-link d-inline-block mr-3"
                    :href="'https://github.com/'+trending.repositorie.owner+'/'+trending.repositorie.name+'/stargazers'"
                    target="_blank"
                  >
                    <svg
                      aria-label="star"
                      class="octicon octicon-star"
                      viewBox="0 0 14 16"
                      version="1.1"
                      width="14"
                      height="16"
                      role="img"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M14 6l-4.9-.64L7 1 4.9 5.36 0 6l3.6 3.26L2.67 14 7 11.67 11.33 14l-.93-4.74L14 6z"
                      ></path>
                    </svg>
                    {{trending.star}}
                  </a>
                  
                  <a
                    class="muted-link d-inline-block mr-3"
                    :href="'https://github.com/'+trending.repositorie.owner+'/'+trending.repositorie.name+'/network'"
                    target="_blank"
                  >
                    <svg
                      aria-label="fork"
                      class="octicon octicon-repo-forked"
                      viewBox="0 0 10 16"
                      version="1.1"
                      width="10"
                      height="16"
                      role="img"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M8 1a1.993 1.993 0 0 0-1 3.72V6L5 8 3 6V4.72A1.993 1.993 0 0 0 2 1a1.993 1.993 0 0 0-1 3.72V6.5l3 3v1.78A1.993 1.993 0 0 0 5 15a1.993 1.993 0 0 0 1-3.72V9.5l3-3V4.72A1.993 1.993 0 0 0 8 1zM2 4.2C1.34 4.2.8 3.65.8 3c0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2zm3 10c-.66 0-1.2-.55-1.2-1.2 0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2zm3-10c-.66 0-1.2-.55-1.2-1.2 0-.65.55-1.2 1.2-1.2.65 0 1.2.55 1.2 1.2 0 .65-.55 1.2-1.2 1.2z"
                      ></path>
                    </svg>
                    {{trending.fork}}
                  </a>
                  <span class="d-inline-block float-sm-right mr-3">
                    <svg
                      class="octicon octicon-star"
                      viewBox="0 0 14 16"
                      version="1.1"
                      width="14"
                      height="16"
                      aria-hidden="true"
                    >
                      <path
                        fill-rule="evenodd"
                        d="M14 6l-4.9-.64L7 1 4.9 5.36 0 6l3.6 3.26L2.67 14 7 11.67 11.33 14l-.93-4.74L14 6z"
                      ></path>
                    </svg>
                    {{trending.newStar}} stars {{timeCodeAliasName}}
                  </span>
                  <span
                    class="d-inline-block float-sm-right mr-3"
                  >{{trending.consecutiveDays}} consecutive {{timeCodeValue}}</span>
                  <span class="d-inline-block float-sm-right">
                    <span v-if="trending.lastRank == ''">
                      <img src="../../../static/plus.png" class="status-img">
                    </span>
                    <span v-else-if="trending.lastRank > trending.rank">
                      <img src="../../../static/rise.png" class="status-img">
                      {{trending.lastRank - trending.rank}}
                    </span>
                    <span v-else-if="trending.lastRank < trending.rank">
                      <img src="../../../static/fall.png" class="status-img">
                      {{trending.rank - trending.lastRank}}
                    </span>
                    <span v-else>
                      <img src="../../../static/line.png" class="status-img">
                    </span>
                  </span>
                </div>
              </li>
              <li style="height:400px; text-align:center;" class="trending-li">
                <span
                  style=" position: relative; top: 50px;"
                  class="d-inline-block float-sm-right mr-3"
                >
                  <a href="https://github.com/Sunnus3">Are you the devil?</a>
                </span>
              </li>
            </ul>
          </el-tab-pane>
        </el-tabs>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import { get } from "../../common/js/axiosi.js";
import { getCookie } from "../../common/js/util.js";
import { setCookie } from "../../common/js/util.js";

var cookieTimeCodeName = "cookie_time_code";
var cookieLanguageCodeName = "cookie_language_code";
var cookieTimeOut = 7; // days

export default {
  name: "TabTrending",
  data() {
    return {
      timeCodeValue: "weekly",
      timeCodes: [],
      timeCodeAliasName: "this week",
      timeCodeMap: {},
      languageCodes: [],
      languageCodeValue: "java",
      languageCodeMap: {},
      languageCodeAliasName: "Java",
      languageSpan: {
        backgroundColor: "#b07219"
      },
      trendings: []
    };
  },
  methods: {
    timeCodeAliasNameChange: function() {
      this.timeCodeAliasName = this.timeCodeMap[this.timeCodeValue];
    },
    languageColorChange: function() {
      this.languageSpan.backgroundColor = this.languageCodeMap[
        this.languageCodeValue
      ].color;
    },
    languageCodeAliasNameChange: function() {
      this.languageCodeAliasName = this.languageCodeMap[
        this.languageCodeValue
      ].aliasName;
    },
    timeCode: function() {
      get("constant/timeCode").then(response => {
        this.timeCodes = response.data.timeCode;
        this.timeCodes.forEach(element => {
          this.timeCodeMap[element.name] = element.aliasName;
        });
        this.timeCodeAliasNameChange();
      });
    },
    languageCode: function() {
      get("/constant/languageCode").then(response => {
        this.languageCodes = response.data.languageCode;
        this.languageCodes.forEach(element => {
          this.languageCodeMap[element.name] = {
            color: element.color,
            aliasName: element.aliasName
          };
        });
        this.languageCodeAliasNameChange();
        this.languageColorChange();
      });
    },
    codeInitByCookie: function() {
      var cookieTimeCode = getCookie(cookieTimeCodeName);
      if (cookieTimeCode != undefined && cookieTimeCode != "") {
        this.timeCodeValue = cookieTimeCode;
      }
      var cookieLanguageCode = getCookie(cookieLanguageCodeName);
      if (cookieLanguageCode != undefined && cookieLanguageCode != "") {
        this.languageCodeValue = cookieLanguageCode;
      }
    },
    trending: function() {
      get("/trending/" + this.languageCodeValue, {
        since: this.timeCodeValue
      }).then(response => {
        this.trendings = response.data.trending;
      });
    },
    codeChange: function() {
      this.trending();
    },
    languageCodeChange: function() {
      this.languageColorChange();
      this.languageCodeAliasNameChange();
      setCookie(cookieLanguageCodeName, this.languageCodeValue, cookieTimeOut);
      this.codeChange();
    },
    timeCodeChange: function() {
      this.timeCodeAliasNameChange();
      setCookie(cookieTimeCodeName, this.timeCodeValue, cookieTimeOut);
      this.codeChange();
    },
    // tab
    handleClick(tab, event) {
      this.timeCodeChange();
    }
  },
  mounted() {
    this.codeInitByCookie();
    this.languageCode();
    this.timeCode();
    this.trending();
  }
};
</script>

<style>
.el-container {
  height: 100%;
  width: 100%;
  padding: 0px;
}

.el-header {
  color: #333;
  text-align: center;
  line-height: 60px;
  padding: 0px;
}

.el-main {
  color: #333;
  text-align: center;
  line-height: 20px;
  padding: 0px;
  width: 50%;
  position: relative;
  left: 25%;
  overflow: hidden;
  height: 100%;
}
.el-select {
  line-height: 20px;
  position: relative;
  left: 35%;
  top: 30px;
  z-index: 100;
}

.trending-ul {
  list-style-type: none;
  width: 700px;
  display: inline-block;
  text-align: left;
}

.trending-li {
  padding: 10px;
  overflow: auto;
  border-bottom: 1px solid #e1e4e8 !important;
}

.repo-language-color {
  border-radius: 50%;
  display: inline-block;
  height: 12px;
  position: relative;
  top: 1px;
  width: 12px;
}
.col-9 {
  width: 70%;
}
.col-12 {
  width: 12%;
}
.ml-0 {
  margin-left: 0 !important;
}

.mb-1 {
  margin-bottom: 4px !important;
}

.vertical-align {
  vertical-align: text-bottom;
  display: inline-block;
  fill: currentColor;
  vertical-align: text-top;
}
.muted-link {
  color: #586069 !important;
}
.d-inline-block {
  display: inline-block !important;
}

.d-block {
  display: block !important;
}

.width-full {
  width: 100% !important;
}

.py-4 {
  padding-bottom: 24px !important;
  padding-top: 24px !important;
}

.mr-3 {
  margin-right: 11px !important;
}
a {
  background-color: transparent;
  text-decoration: none;
}

.text-gray {
  color: #586069 !important;
}
.pr-4 {
  padding-right: 24px !important;
}
.py-1 {
  padding-bottom: 4px !important;
  padding-top: 4px !important;
}

.repo-list-item {
  position: relative;
}

.explore-content {
  margin-top: -15px;
}
* {
  box-sizing: border-box;
}
.status-img {
  height: 15px;
  width: 15px;
  position: relative;
  top: 3px;
}
svg {
  position: relative;
  top: 2px;
}

.el-tabs__content {
  height: 1000px;
  overflow: scroll;
}
</style>

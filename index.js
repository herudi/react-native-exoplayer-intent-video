import React, {
  PropTypes,
  Component
} from 'react';

import {
  NativeModules
} from 'react-native';

const { ExoPlayerManager } = NativeModules;

module.exports = {
  ...ExoPlayerManager,
  play(data) {
  	var url = data.url;
  	var title = data.title === undefined ? null : data.title;
  	var subtitle = data.subtitle === undefined ? null : data.subtitle;
    return ExoPlayerManager.showVideoPlayer(url,title,subtitle);
  }
}
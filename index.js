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
  play(url) {
    return ExoPlayerManager.showVideoPlayer(url);
  }
}
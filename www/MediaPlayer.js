var PLUGIN_ID = 'tabris-plugin-mediaplayer';

var MediaPlayer = tabris.NativeObject.extend('ru.fax1ty.tabris.mediaplayer.MediaPlayer');

MediaPlayer.prototype._listen = function (name, listening) {
  this._nativeListen(name, listening);
};

MediaPlayer.prototype._trigger = function (name, event) {
  tabris.Widget.prototype._trigger.call(this, name, event);
};

Object.assign(MediaPlayer.prototype, {
  _nativeCreate: function () {
    tabris.NativeObject.prototype._nativeCreate.apply(this, arguments);
    return this;
  },

  play: function () {
    this._nativeCall('play');
  },

  pause: function () {
    this._nativeCall('pause');
  },

  stop: function () {
    this._nativeCall('stop');
  },

  seekTo: function (position) {
    if (typeof position != 'number') throw 'Argument "position" must be a number';
    this._nativeCall('seekTo', { position: position });
  },

  getDuration: function () {
    this._nativeCall('getDuration');
  },

  isPlaying: function () {
    this._nativeCall('isPlaying');
  },

  isLooping: function () {
    this._nativeCall('isLooping');
  },

  setIsLooping: function (loop) {
    if (typeof loop != 'boolean') throw 'Argument "loop" must be a boolean';
    else this._nativeCall('setIsLooping', { loop: loop });
  },

  getCurrentPosition: function () {
    this._nativeCall('getCurrentPosition', { loop: loop });
  },

  dispose: function () {
    this._dispose();
  }
});

tabris.NativeObject.defineProperties(Map.prototype, {
  url: { type: 'string', nocache: true },
  loop: { type: 'boolean', nocache: true },
  autoPlay: { type: 'boolean', default: false }
});

module.exports = MediaPlayer;

class AudioPlayer extends tabris.NativeObject {
  constructor(properties) {
    super(properties);
  }

  get _nativeType() {
    return 'ru.fax1ty.tabris.audioplayer.AudioPlayer';
  }

  play() {
    this._nativeCall('play');
  }

  pause() {
    this._nativeCall('pause');
  }

  seekTo(position) {
    if (typeof position != 'number') throw new Error('To call this method you must to pass number argument "position", but you passed ' + position);
    else this._nativeCall('seekTo', { position: position });
  }
}

tabris.NativeObject.defineEvents(AudioPlayer.prototype, {
  error: { native: true },
  ready: { native: true },
  complete: { native: true }
});

tabris.NativeObject.defineProperties(AudioPlayer.prototype, {
  'url': {
    type: 'string',
    default: '',
    nocache: true
  },
  'autoPlay': {
    type: 'boolean',
    default: false,
    nocache: true
  },
  'loop': {
    type: 'boolean',
    default: false,
    nocache: true
  },
  'position': {
    type: 'number',
    default: 0,
    nocache: true
  },
  'isPlaying': {
    type: 'boolean',
    default: false,
    nocache: true
  }
});

module.exports = AudioPlayer;
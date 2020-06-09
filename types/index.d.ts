import { NativeObject } from 'tabris';

declare global {
    class AudioPlayer extends NativeObject {
        position: number;
        loop: boolean;
        isPlaying: boolean;

        play(): void;
        pause(): void;

        constructor(values: { url: string, loop?: boolean, autoPlay?: boolean });
    }
}
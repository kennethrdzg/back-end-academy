export interface Post {
    id: number,
    userId: number,
    username: string,
    content: string,
    timestamp: Date,
    likes: number,
    liked: boolean
}
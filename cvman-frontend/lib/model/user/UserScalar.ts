import { z } from "zod"

export const userListOutSchema = z.array(
  z.object({
    id: z.number(),
    firstName: z.string(),
    lastName: z.string(),
    shortDescription: z.string()
  })
)
export type UserListOut = z.infer<typeof userListOutSchema>

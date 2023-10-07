import { z } from "zod"
import { activitySchema } from "../activity/activityScalar"

export const cvSchema = z.object({
  id: z.number(),
  name: z.string(),
  activities: z.array(activitySchema)
})

export type Cv = z.infer<typeof cvSchema>

export const userDetailsOutCvSchema = cvSchema
